package game;


import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.config.TetrisConstants;
import game.input.Keypad;
import game.input.MouseMotion;
import game.input.Tick;
import game.model.Command;
import game.model.EventQueue;
import game.model.Page;
import game.model.Shape;
import game.model.Space;
import game.ui.GamePanel;
import game.ui.SidePanel;
import game.ui.Window;

public class App {
	
	private static App app;
	
	private Window      win;
	private GamePanel   panel;
	private SidePanel   sidePanel;
	private Space       space;
	private EventQueue  queue;
	private Shape       shape;
	private Page        shapePic;
	private Page        shapePicImg;
	private Tick        tick;
	private boolean     isPaused;

	
	private String shapeQueue = "ITOFLSZ";
	private int shapeIndex = 0;

	
	//Integer.toBinaryString(-1);
	public static void main(String[] args)
	{
		app = new App();
		
		try {
			app.play();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static App getInstance() {
		return app;
	}
	
	private App()
	{
		int unit = TetrisConstants.TILE_SIZE;
		int size = TetrisConstants.MAX_SHAPE_SIZE;
		
		win         = new Window();
		panel       = new GamePanel();
		sidePanel   = new SidePanel();
		queue       = new EventQueue();
		space       = new Space();
		tick        = new Tick();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		genNextShape();
		
		win.add(panel);
		win.add(sidePanel, BorderLayout.EAST);
		win.pack();
		
		win.addKeyListener(new Keypad());
		win.addMouseMotionListener(new MouseMotion());
		win.setVisible(true);
	}
	
	private void refreshUI()
	{
		win.repaint();
	}
	
	private void play() throws InterruptedException
	{
		// 
		//tick.start();
		//isPaused = false;
		
		while (true)
		{
			Command cmd = queue.take();
			
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
			case ROTATE_CW:
				rotateShape();
				refreshUI();
				break;
			case NEXT_SHAPE:
				genNextShape();
				break;
			case PAUSE:
				pause();
				break;
			case RESUME:
				resume();
				break;
			case TEST:
				break;
			default: 
				System.out.println("Error: unknown Command");
			break;
			}
		}
		
		//System.out.println("end play ...");
	}
	
	/**
	 * ��ͣ��Ϸ
	 */
	private void pause()
	{
		if (!isPaused) {
		
			isPaused = true;
			tick.stop();
		}
		
		refreshUI();
	}
	
	private void resume()
	{
		if (isPaused)
		{
			isPaused = false;
			tick.start();
		}
		
		refreshUI();
	}
	
	public boolean isPaused()
	{
		return isPaused;
	}
	
	public EventQueue getQueue()
	{
		return queue;
	}
	
	/**
	 * ������һ������
	 */
	private void genNextShape()
	{
		char type = shapeQueue.charAt(shapeIndex);
		
		shape = Shape.create(type);
		updateShapePic();
		
		shapeIndex ++;
		if (shapeIndex >= shapeQueue.length())
			shapeIndex = 0;
		
		shapeImageFall();
		
		refreshUI();
	}
	
	public Shape currentShape()
	{
		return shape;
	}
	
	public void moveShapeLeft()
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
	
	public void moveShapeRight()
	{
		shape.right();
		
		if (space.isConflict(shape))
			shape.left();
		
		shapeImageFall();
		refreshUI();
	}
	
	
	/**
	 * �ƶ���������
	 */
	public void moveShapeDown()
	{
		shape.down();
		
		// ����֮�����Ƿ񴥵�
		if (space.isConflict(shape)) {
			
			// ����
			shape.up();
			// �ϲ�
			space.mergeFrom(shape);
			// ���
			space.clearFullRows();
			// ������Ϸ�ж�
			//space.checkGameOver();
			// ������һ������
			genNextShape();
		}
		
		refreshUI();
	}
	
	
	private void rotateShape()
	{
		Shape nextShape = shape.rotate(true);
		
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
		refreshUI();
	}
	
	public BufferedImage snapshot()
	{
		return space.getImage();
	}
	
	public void hideMouse()
	{
		win.hideCursor();
	}
	
	public void showMouse()
	{
		win.showCursor();
	}
	
	public Page getShapePic()
	{
		return shapePic;
	}
	
	public Page getShapePicImg()
	{
		return shapePicImg;
	}
	
	private void updateShapePic()
	{
		Composite comp = AlphaComposite.getInstance(AlphaComposite.CLEAR);
		Composite compBackup;
		Graphics2D g;
		
		g = shapePic.getContext();
		compBackup = g.getComposite();
		g.setComposite(comp);
		g.fillRect(0, 0, shapePic.getWidth(), shapePic.getHeight());
		
		g.setComposite(compBackup);
		g.setColor(TetrisConstants.COLOR_TILE);
		shape.paint(g);
		g.dispose();
		
		g = shapePicImg.getContext();
		compBackup = g.getComposite();
		g.setComposite(comp);
		g.fillRect(0, 0, shapePicImg.getWidth(), shapePicImg.getHeight());
		
		g.setComposite(compBackup);
		g.setColor(TetrisConstants.COLOR_IMAGE);
		shape.paint(g);
		g.dispose();
	}
}

