package game;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import game.config.TetrisConstants;
import game.model.Command;
import game.model.EventQueue;
import game.model.MusicEvent;
import game.model.Page;
import game.model.Shape;
import game.model.ShapeFactory;
import game.model.Space;
import game.model.Status;
import game.sound.Pianist;
import game.sound.RealPlayer;
import game.ui.Window;

/**
 * 游戏实例在一个单独的线程运行
 */
public class App extends Thread {
	
	/**
	 * 游戏结束信号
	 */
	private class GameOverSig extends Exception 
	{
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * 心跳
	 * 
	 * 心跳驱动游戏时间前进
	 */
	private class HeartBeat extends TimerTask {
		
		@Override
		public void run()
		{
			// 心跳转化为方块下落的动力
			App.this.queue.offer(Command.DOWN);
		}
	}
	
	/**
	 * 控制游戏心跳
	 */
	private class Tick2 {
		
		private Timer timer;
		private HeartBeat task;
		
		public void stop()
		{
			if (timer == null)
				return;
			
			timer.cancel();
			timer = null;
			
			task.cancel();
			task = null;
		}
		
		public void start()
		{
			if (timer != null)
				stop();
			
			timer = new Timer();
			task = new HeartBeat();
			timer.schedule(task, 2000, 1000);
		}
	}
	
	/**
	 * 游戏实例计数
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
	private Tick2       tick2;

	/**
	 * 清理游戏实例
	 */
	synchronized public void dispose()
	{
		win = null;
		
		pianist.dispose();
		
		if (this.isAlive())
		{
			try {
				// 必须放入队伍中, 不能丢失, 
				// 否则游戏实例无法停止
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
		
		// 游戏初始状态
		status = Status.READY;
		
		space       = new Space();
		factory     = new ShapeFactory();
		queue       = new EventQueue();
		tick2       = new Tick2();
		
		pianist     = new Pianist();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		nextShapePic = new Page(size * unit_s, size * unit_s);
		genShape();
		
		this.setName("app-" + count);
		count ++;
	}
	
	/**
	 * 重绘界面
	 * 
	 * 在执行 dispose() 之后，游戏实例与界面解除绑定，
	 * 所以重绘界面时需要检查窗口是否存在
	 */
	private void refreshUI()
	{
		if (win != null)
		{
			win.repaint();
		}
	}
	
	/**
	 * 处理游戏各种操作
	 * 
	 * 注意游戏当前的状态
	 * 
	 * @throws GameOverSig 
	 */
	private void play(Command cmd) throws GameOverSig
	{
		// 如果游戏已经结束，则不再响应任何操作命令
		if (status == Status.END)
			throw new GameOverSig();
		
		// 如果游戏尚未开始，则不可操作
		// 开始游戏需要单独调用 gameStart();
		if (status == Status.READY)
			return;
		
		// 暂停中，只能执行 RESUME
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
	 * 游戏暂停
	 * 
	 * RUNNING -> PAUSE
	 */
	private void pauseGame()
	{
		if (status == Status.RUNNING) {
			status = Status.PAUSE;
			tick2.stop();
			refreshUI();
		}
	}
	
	/**
	 * 游戏继续
	 * 
	 * PAUSE -> RUNNING
	 */
	private void resumeGame()
	{
		if (status == Status.PAUSE)
		{
			status = Status.RUNNING;
			tick2.start();
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
	 * @throws GameOverSig 
	 */
	private void moveShapeDown() throws GameOverSig
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
	 * @throws GameOverSig 
	 */
	private void gameOver() throws GameOverSig
	{
		status = Status.END;
		
		tick2.stop();
		
		refreshUI();

		// 播放结束音乐
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
	 * 接收命令
	 * 
	 * 接收到的命令需要排队，依次等待执行
	 * 
	 * 游戏开始后才能接收命令, 不然命令会一直在队伍中
	 * 堆积, 而没有线程来处理
	 */
	public void putCommand(Command cmd)
	{
		// put 方法会一直等待
		//queue.put(cmd);
		//queue.add(cmd);

		if (this.isAlive())
		{
			queue.offer(cmd);
		}
	}

	/**
	 * 开始游戏
	 * 
	 * READY -> RUNNING
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		// 如果当前不是就绪状态，立即返回什么也不用做
		if (status != Status.READY)
			return;
		
		System.out.println("App begin running ....");
		String str = Thread.currentThread().getName();
		System.out.println("Thread name is : " + str);
		
		
		status = Status.RUNNING;
		
		tick2.start();
		
		
		try {
			while (true)
			{
				Command cmd = queue.take();
				
				// 优先处理 KILL 命令
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
		
		System.out.println("Thread " + Thread.currentThread().getName() + " reaches END!");
		
		// 游戏结束 - 清理一部分资源
		
		// 不解除与 win 的绑定. 
		// 游戏结束界面仍然可以在窗口显示
		
		// 停止定时器
		tick2.stop();
	}
}

