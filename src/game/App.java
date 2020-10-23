package game;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledFuture;

import game.config.TetrisConstants;
import game.model.Command;
import game.model.EventQueue;
import game.model.MusicEvent;
import game.model.Page;
import game.model.Shape;
import game.model.ShapeFactory;
import game.model.Space;
import game.model.Status;
import game.model.Task;
import game.sound.Pianist;
import game.sound.RealPlayer;
import game.ui.Window;

/**
 * ��Ϸʵ����һ���������߳�����
 */
public class App extends Thread {
	
	/**
	 * ��Ϸ�����ź�
	 */
	private class GameOverSig extends Exception 
	{
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * ����
	 * 
	 * ����������Ϸʱ��ǰ��
	 */
	private class HeartBeat implements Runnable {
		
		@Override
		public void run()
		{
			// ����ת��Ϊ��������Ķ���
			App.this.queue.offer(Command.DOWN);
		}
	}
	
		

	
	/**
	 * ��Ϸʵ������
	 */
	private static int count = 0;
	
	
	
	
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
	private HeartBeat   task2;
	private ScheduledFuture<?> future;


	/**
	 * ������Ϸʵ��
	 */
	synchronized public void dispose()
	{
		win = null;
		
		pianist.dispose();
		
		if (this.isAlive())
		{
			try {
				// ������������, ���ܶ�ʧ, 
				// ������Ϸʵ���޷�ֹͣ
				queue.put(Command.KILL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		task2       = new HeartBeat();
		future      = null;
		
		pianist     = new Pianist();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		nextShapePic = new Page(size * unit_s, size * unit_s);
		genShape();
		
		this.setName("app-" + count);
		count ++;
	}
	
	/**
	 * �ػ����
	 * 
	 * ��ִ�� dispose() ֮����Ϸʵ����������󶨣�
	 * �����ػ����ʱ��Ҫ��鴰���Ƿ����
	 */
	private void refreshUI()
	{
		if (win != null)
		{
			win.repaint();
		}
	}
	
	/**
	 * ������Ϸ���ֲ���
	 * 
	 * ע����Ϸ��ǰ��״̬
	 * 
	 * @throws GameOverSig 
	 */
	private void play(Command cmd) throws GameOverSig
	{
		// �����Ϸ�Ѿ�������������Ӧ�κβ�������
		if (status == Status.END)
			throw new GameOverSig();
		
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
	 * ��ʼ��ʱ
	 */
	private void startTick()
	{
		if (future == null)
		{
			future = Task.addTick(task2);
		}
	}
	
	/**
	 * ֹͣ��ʱ
	 */
	private void stopTick()
	{
		if (future != null)
		{
			future.cancel(true);
			future = null;
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
			stopTick();
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
			startTick();
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
	 * @throws GameOverSig 
	 */
	private void moveShapeDown() throws GameOverSig
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
	 * @throws GameOverSig 
	 */
	private void gameOver() throws GameOverSig
	{
		status = Status.END;
		
		stopTick();
		
		refreshUI();

		// ���Ž�������
		RealPlayer.execute(MusicEvent.GAME_OVER);
		
		throw new GameOverSig();
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
	 * ��������
	 * 
	 * ���յ���������Ҫ�Ŷӣ����εȴ�ִ��
	 * 
	 * ��Ϸ��ʼ����ܽ�������, ��Ȼ�����һֱ�ڶ�����
	 * �ѻ�, ��û���߳�������
	 */
	public void putCommand(Command cmd)
	{
		// put ������һֱ�ȴ�
		//queue.put(cmd);
		//queue.add(cmd);

		if (this.isAlive())
		{
			queue.offer(cmd);
		}
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
		
		// �����ǰ���Ǿ���״̬����������ʲôҲ������
		if (status != Status.READY)
			return;
		
		
		status = Status.RUNNING;
		startTick();
		
		try {
			while (true)
			{
				Command cmd = queue.take();
				
				// ���ȴ��� KILL ����
				if (cmd == Command.KILL)
					break;
				
				if (isPaused() && cmd == Command.PAUSE)
					cmd = Command.RESUME;
				
				play(cmd);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameOverSig e) {
			System.out.println("catch signal: GameOver");
		}
		
		// ================================
		// ��Ϸ���� - ����һ������Դ
		// ================================
		
		// ������� win �İ�. 
		// ��Ϸ����������Ȼ�����ڴ�����ʾ
		
		// ֹͣ��ʱ��
		stopTick();
	}
}

