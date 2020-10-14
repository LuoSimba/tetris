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
import game.signal.GameOverSignal;
import game.sound.Pianist;
import game.ui.Window;

public class App {
	
	private Window      win;
	private Space       space;
	private EventQueue  queue;
	private ShapeFactory factory;
	private Shape       shape;
	private Shape       nextShape;
	private Page        shapePic;
	private Page        shapePicImg;
	private Page        nextShapePic;
	private Tick        tick;
	private Pianist     pianist;
	private boolean     isPaused;
	private boolean     isGameOver;
	private int         score;

	public void dispose()
	{
		win.setApp(null);
		win = null;
	}
	
	protected App(Window win)
	{
		this.win = win;
		
		int unit = TetrisConstants.TILE_SIZE;
		int unit_s = TetrisConstants.TILE_SIZE_SMALL;
		int size = TetrisConstants.MAX_SHAPE_SIZE;
		
		score    = 0;
		isGameOver  = false;
		
		queue       = new EventQueue();
		space       = new Space();
		tick        = new Tick(this);
		factory     = new ShapeFactory();
		
		pianist     = new Pianist();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		nextShapePic = new Page(size * unit_s, size * unit_s);
		genShape();
		
		// BUGFIX: 只有当实例完全初始化完成后，才能告知 UI 界面
		// 如果一开始就关联到 Window， UI 在绘制方块时，可能报空指针异常，因为这时候实例可能
		// 还来不及创建 Shape.
		win.setApp(this);
	}
	
	private void refreshUI()
	{
		win.repaint();
	}
	
	protected void play() throws InterruptedException, GameOverSignal
	{
		tick.start();
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
			case ROTATE:
				rotateShape();
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
	
	public boolean isGameOver()
	{
		return isGameOver;
	}
	
	public EventQueue getQueue()
	{
		return queue;
	}
	
	/**
	 * 产生下一个方块
	 */
	private void genShape()
	{
		// 当前方块
		if (nextShape == null)
		{
			shape = factory.genNext();
		}
		else
		{
			shape = nextShape;
		}
		
		// 产生下一个方块，下一个方块总是新生成的
		nextShape = factory.genNext();

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
	 * @throws GameOverSignal 游戏结束
	 */
	public void moveShapeDown() throws GameOverSignal
	{
		shape.down();
		
		// 下落之后检查是否触底
		if (space.isConflict(shape)) {
			
			// 回退
			shape.up();
			// 合并
			space.mergeShape(shape, shapePic);
			pianist.playKnock();
			
			// 清除
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
			
			// 结束游戏判断
			if (space.isOverflow())
			{
				gameOver();
			}
			
			// 生成下一个方块
			genShape();
		}
		
		refreshUI();
	}
	
	/**
	 * 游戏结束
	 */
	private void gameOver() throws GameOverSignal
	{
		isGameOver = true;
		
		tick.stop();
		
		refreshUI();
		
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
	
	public BufferedImage snapshot()
	{
		return space.getImage();
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
	
	public int getScore()
	{
		return score;
	}
}

