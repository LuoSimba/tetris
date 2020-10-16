package game;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.config.TetrisConstants;
import game.input.Tick;
import game.model.Command;
import game.model.Page;
import game.model.Shape;
import game.model.ShapeFactory;
import game.model.Space;
import game.model.Status;
import game.signal.GameOverSignal;
import game.sound.Pianist;
import game.ui.Window;

public class App {
	
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
	private Tick        tick;

	public void dispose()
	{
		win.setApp(null);
		win = null;
	}
	
	public App(Window win)
	{
		this.win = win;
		
		int unit = TetrisConstants.TILE_SIZE;
		int unit_s = TetrisConstants.TILE_SIZE_SMALL;
		int size = TetrisConstants.MAX_SHAPE_SIZE;
		
		score    = 0;
		
		// 游戏初始状态
		status = Status.READY;
		
		space       = new Space();
		factory     = new ShapeFactory();
		tick        = new Tick(this);
		
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
	
	/**
	 * 处理游戏各种操作
	 * 
	 * 注意游戏当前的状态
	 */
	synchronized public void play(Command cmd) throws GameOverSignal
	{
		// 如果游戏已经结束，则不再响应任何操作命令
		if (status == Status.END)
			throw new GameOverSignal();
		
		// 如果游戏尚未开始，则不可操作
		// 开始游戏需要单独调用 gameStart();
		if (status == Status.READY)
			return;
		
		// 暂停中，只能执行 RESUME
		if (status == Status.PAUSE)
		{
			if (cmd == Command.RESUME)
			{
				resume();
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
				pause();
				break;
			default: 
				break;
			}
		}
	}
	
	/**
	 * 游戏暂停
	 * 
	 * RUNNING -> PAUSE
	 */
	private void pause()
	{
		if (status == Status.RUNNING) {
			status = Status.PAUSE;
			tick.stop();
			refreshUI();
		}
	}
	
	/**
	 * 游戏继续
	 * 
	 * PAUSE -> RUNNING
	 */
	private void resume()
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
	 * 移动方块下落
	 * @throws GameOverSignal 游戏结束
	 */
	private void moveShapeDown() throws GameOverSignal
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
	 * 开始游戏
	 * 
	 * READY -> RUNNING
	 */
	synchronized public void gameStart()
	{
		if (status == Status.READY)
		{
			status = Status.RUNNING;
			tick.start();
		}
	}
	
	/**
	 * 游戏结束
	 */
	private void gameOver() throws GameOverSignal
	{
		status = Status.END;
		
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
	 * 心跳
	 * 
	 * 心跳指示游戏时间前进
	 */
	public void beat()
	{
		try {
			// 心跳转化为方块下落的动力
			play(Command.DOWN);
		} catch (GameOverSignal e) {
			
			// 自身收到游戏结束消息，
			// 仅仅刷新界面
			// 异常主要是用来通知外部的。
			refreshUI();
		}
	}
}

