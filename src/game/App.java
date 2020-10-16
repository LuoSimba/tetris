package game;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.config.TetrisConstants;
import game.input.Tick;
import game.model.Command;
import game.model.EventQueue;
import game.model.Page;
import game.model.Shape;
import game.model.ShapeFactory;
import game.model.Space;
import game.model.Status;
import game.signal.GameOverSignal;
import game.sound.Pianist;
import game.sound.RealPlayer;
import game.ui.Window;

/**
 * ��Ϸʵ����һ���������߳�����
 */
public class App extends Thread {
	
	private Window      win;
	private Space       space;
	private ShapeFactory factory;
	private Shape       shape;
	private Shape       nextShape;
	private Page        shapePic;
	private Page        shapePicImg;
	private Page        nextShapePic;
	private Pianist     pianist;
	private Status      status;
	private int         score;
	private EventQueue  queue;
	private Tick        tick;

	public void dispose()
	{
		win = null;
	}
	
	public App(Window win)
	{
		this.win = win;
		
		int unit = TetrisConstants.TILE_SIZE;
		int unit_s = TetrisConstants.TILE_SIZE_SMALL;
		int size = TetrisConstants.MAX_SHAPE_SIZE;
		
		score    = 0;
		
		// ��Ϸ��ʼ״̬
		status = Status.READY;
		
		space       = new Space();
		factory     = new ShapeFactory();
		queue       = new EventQueue();
		tick        = new Tick(this);
		
		pianist     = new Pianist();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		nextShapePic = new Page(size * unit_s, size * unit_s);
		genShape();
	}
	
	private void refreshUI()
	{
		win.repaint();
	}
	
	/**
	 * ������Ϸ���ֲ���
	 * 
	 * ע����Ϸ��ǰ��״̬
	 */
	synchronized public void play(Command cmd) throws GameOverSignal
	{
		// �����Ϸ�Ѿ�������������Ӧ�κβ�������
		if (status == Status.END)
			throw new GameOverSignal();
		
		// �����Ϸ��δ��ʼ���򲻿ɲ���
		// ��ʼ��Ϸ��Ҫ�������� gameStart();
		if (status == Status.READY)
			return;
		
		// ��ͣ�У�ֻ��ִ�� RESUME
		if (status == Status.PAUSE)
		{
			if (cmd == Command.RESUME)
			{
				resumeGame();
			}
		}
		else if (status == Status.RUNNING)
		{
			switch (cmd)
			{
			case LEFT:
				moveShapeLeft();
				break;
			case RIGHT:
				moveShapeRight();
				break;
			case DOWN:
				moveShapeDown();
				break;
			case ROTATE:
				rotateShape();
				break;
			case NEXT_SHAPE:
				genShape();
				break;
			case PAUSE:
				pauseGame();
				break;
			default: 
				break;
			}
		}
	}
	
	/**
	 * ��Ϸ��ͣ
	 * 
	 * RUNNING -> PAUSE
	 */
	private void pauseGame()
	{
		if (status == Status.RUNNING) {
			status = Status.PAUSE;
			tick.stop();
			refreshUI();
		}
	}
	
	/**
	 * ��Ϸ����
	 * 
	 * PAUSE -> RUNNING
	 */
	private void resumeGame()
	{
		if (status == Status.PAUSE)
		{
			status = Status.RUNNING;
			tick.start();
			refreshUI();
		}
	}
	
	synchronized public boolean isPaused()
	{
		return status == Status.PAUSE;
	}
	
	synchronized public boolean isGameOver()
	{
		return status == Status.END;
	}
	
	/**
	 * ������һ������
	 */
	private void genShape()
	{
		// ��ǰ����
		if (nextShape == null)
		{
			shape = factory.genNext();
		}
		else
		{
			shape = nextShape;
		}
		
		// ������һ�����飬��һ���������������ɵ�
		nextShape = factory.genNext();

		// �������������Ϻ��ٻ��Ʒ���ͼ��
		updateShapePic();
		
		shapeImageFall();
		refreshUI();
	}
	
	synchronized public Shape currentShape()
	{
		return shape;
	}
	
	private void moveShapeLeft()
	{
		shape.left();
		
		if (space.isConflict(shape))
			shape.right();
		
		shapeImageFall();
		refreshUI();
	}
	
	private void shapeImageFall()
	{
		shape.restoreImage();
		
		while (!space.isConflictImage(shape)) {
			shape.imageDown();
		}
		
		shape.imageUp();
	}
	
	private void moveShapeRight()
	{
		shape.right();
		
		if (space.isConflict(shape))
			shape.left();
		
		shapeImageFall();
		refreshUI();
	}
	
	
	/**
	 * �ƶ���������
	 * @throws GameOverSignal ��Ϸ����
	 */
	private void moveShapeDown() throws GameOverSignal
	{
		shape.down();
		
		// ����֮�����Ƿ񴥵�
		if (space.isConflict(shape)) {
			
			// ����
			shape.up();
			// �ϲ�
			space.mergeShape(shape, shapePic);
			pianist.playKnock();
			
			// ���
			int rows = space.clearFullRows();
			
			if (rows >= 4)
			{
				score += 6;
				pianist.ding();
			}
			else if (rows == 3)
			{
				score += 4;
				pianist.ding();
			}
			else if (rows == 2)
			{
				score += 2;
				pianist.ding();
			}
			else if (rows == 1)
			{
				score ++;
				pianist.ding();
			}
			
			// ������Ϸ�ж�
			if (space.isOverflow())
			{
				gameOver();
			}
			
			// ������һ������
			genShape();
		}
		
		refreshUI();
	}
	
	/**
	 * ��Ϸ����
	 */
	private void gameOver() throws GameOverSignal
	{
		status = Status.END;
		
		tick.stop();
		
		refreshUI();

		// ���Ž�������
		RealPlayer.getInstance().playGameOver();
		
		throw new GameOverSignal();
	}
	
	private void rotateShape()
	{
		Shape nextShape = factory.rotate(shape);
		
		if (space.isConflict(nextShape))
		{
			nextShape.left();
			
			if (space.isConflict(nextShape))
			{
				nextShape.right();
				nextShape.right();
				
				if (space.isConflict(nextShape))
					return;
			}
		}
		
		shape = nextShape;
		updateShapePic();
		
		shapeImageFall();
		pianist.playRotate();
		refreshUI();
	}
	
	synchronized public BufferedImage snapshot()
	{
		return space.getImage();
	}
	
	synchronized public Page getShapePic()
	{
		return shapePic;
	}
	
	synchronized public Page getShapePicImg()
	{
		return shapePicImg;
	}
	
	synchronized public Page getNextShapePic()
	{
		return nextShapePic;
	}
	
	private void updateShapePic()
	{
		Graphics2D g;
		
		shapePic.clear();
		g = shapePic.getContext();
		shape.paintBody(g);
		g.dispose();
		
		shapePicImg.clear();
		g = shapePicImg.getContext();
		shape.paintImage(g);
		g.dispose();
		
		nextShapePic.clear();
		g = nextShapePic.getContext();
		nextShape.paintPre(g);
		g.dispose();
	}
	
	synchronized public int getScore()
	{
		return score;
	}
	
	/**
	 * ����
	 * 
	 * ����ָʾ��Ϸʱ��ǰ��
	 */
	public void beat()
	{
		try {
			// ����ת��Ϊ��������Ķ���
			play(Command.DOWN);
		} catch (GameOverSignal e) {
			
			// �����յ���Ϸ������Ϣ��
			// ����ˢ�½���
			// �쳣��Ҫ������֪ͨ�ⲿ�ġ�
			refreshUI();
		}
	}
	
	/**
	 * ��������
	 * 
	 * ���յ���������Ҫ�Ŷӣ����εȴ�ִ��
	 */
	public void putCommand(Command cmd)
	{
		// put ������һֱ�ȴ�
		//queue.put(cmd);
		//queue.add(cmd);

		queue.offer(cmd);
	}

	/**
	 * ��ʼ��Ϸ
	 * 
	 * READY -> RUNNING
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		if (status == Status.READY)
		{
			status = Status.RUNNING;
			
			tick.start();
			
			while (true)
			{
				try {
					Command cmd = queue.take();
					
					if (isPaused() && cmd == Command.PAUSE)
						cmd = Command.RESUME;
					
					play(cmd);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					break;
				} catch (GameOverSignal e) {
					break;
				}
			}
		}
	}
}

