package game;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.config.TetrisConstants;
import game.input.Keypad;
import game.input.MouseMotion;
import game.input.Tick;
import game.model.ColorScheme;
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
	private Shape       nextShape;
	private Page        shapePic;
	private Page        shapePicImg;
	private Page        nextShapePic;
	private Tick        tick;
	private ColorScheme cs;
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
		int unit_s = TetrisConstants.TILE_SIZE_SMALL;
		int size = TetrisConstants.MAX_SHAPE_SIZE;
		
		cs          = new ColorScheme();
		win         = new Window();
		panel       = new GamePanel();
		sidePanel   = new SidePanel();
		queue       = new EventQueue();
		space       = new Space();
		tick        = new Tick();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		nextShapePic = new Page(size * unit_s, size * unit_s);
		genShape();
		
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
				genShape();
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
	 * 暂停游戏
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
	 * 按照一定的顺序产生方块
	 */
	private Shape createShape()
	{
		char type = shapeQueue.charAt(shapeIndex);
		
		shapeIndex ++;
		if (shapeIndex >= shapeQueue.length())
			shapeIndex = 0;
		
		return Shape.create(type);
	}
	
	/**
	 * 产生下一个方块
	 */
	private void genShape()
	{
		// 当前方块
		if (nextShape == null)
		{
			shape = createShape();
		}
		else
		{
			shape = nextShape;
		}
		// 产生新方块的时候，才更新配色
		cs.next();
		
		// 产生下一个方块，下一个方块总是新生成的
		nextShape = createShape();

		// 两个方块产生完毕后，再绘制方块图形
		updateShapePic();
		
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
	 * 移动方块下落
	 */
	public void moveShapeDown()
	{
		shape.down();
		
		// 下落之后检查是否触底
		if (space.isConflict(shape)) {
			
			// 回退
			shape.up();
			// 合并
			space.mergeShape(shape, shapePic);
			// 清除
			space.clearFullRows();
			// 结束游戏判断
			//space.checkGameOver();
			// 生成下一个方块
			genShape();
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
	
	public Page getNextShapePic()
	{
		return nextShapePic;
	}
	
	private void updateShapePic()
	{
		Graphics2D g;
		int unit = TetrisConstants.TILE_SIZE;
		int unit_s = TetrisConstants.TILE_SIZE_SMALL;
		
		shapePic.clear();
		g = shapePic.getContext();
		g.setColor(cs.getColor());
		shape.paint(g, unit);
		g.dispose();
		
		shapePicImg.clear();
		g = shapePicImg.getContext();
		g.setColor(cs.getImgColor());
		shape.paint(g, unit);
		g.dispose();
		
		nextShapePic.clear();
		g = nextShapePic.getContext();
		g.setColor(Color.RED);
		nextShape.paint(g, unit_s);
		g.dispose();
	}
}

