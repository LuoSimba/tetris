package game;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.concurrent.ScheduledFuture;

import game.config.TetrisConstants;
import game.model.Command;
import game.model.EventQueue;
import game.model.GameListener;
import game.model.Level;
import game.model.MusicEvent;
import game.model.Page;
import game.model.Brick;
import game.model.BrickFactory;
import game.model.Space;
import game.model.Status;
import game.model.Task;
import game.model.TaskService;
import game.sound.Pianist;
import game.sound.RealPlayer;
import game.ui.GameView;

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
	private class HeartBeat implements Task {
		
		private ScheduledFuture<?> future;
		
		@Override
		public void run()
		{
			// 心跳转化为方块下落的动力
			App.this.queue.offer(Command.DOWN);
		}

		@Override
		synchronized public void cancel() {
			if (future != null)
			{
				future.cancel(true);
				future = null;
			}
		}

		@Override
		synchronized public void setHandle(ScheduledFuture<?> future) {
			this.future = future;
		}

		@Override
		synchronized public boolean isRunning() {
			return future != null;
		}

		@Override
		synchronized public void start() {
			if (!this.isRunning())
			{
				TaskService.addTick(this);
			}
		}
		
		@Override
		public int getInterval() {
			return App.this.levelMgr.getSpeed();
		}
	}
	
		

	
	/**
	 * 游戏实例计数
	 */
	private static int count = 0;
	
	
	
	
	private GameView    view;
	private Space       space;
	private BrickFactory factory;
	private Brick       shape;
	private Brick       nextShape;
	private Page        shapePic;
	private Page        shapePicImg;
	private Page        nextShapePic;
	private Pianist     pianist;
	private Status      status;
	private EventQueue  queue;
	private HeartBeat   task2;
	private Level       levelMgr;
	
	private HashSet<GameListener> listeners;


	/**
	 * 清理游戏实例
	 */
	synchronized public void dispose()
	{
		// 解除与视图的绑定
		view = null;
		
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
		
		for (GameListener l : listeners)
		{
			l.onDispose();
		}
	}
	
	public App(GameView view)
	{
		// 关联视图
		this.view = view;
		
		int unit = TetrisConstants.TILE_SIZE;
		int unit_s = TetrisConstants.TILE_SIZE_SMALL;
		int size = TetrisConstants.MAX_SHAPE_SIZE;
		
		
		// 游戏初始状态
		status = Status.READY;
		
		space       = new Space();
		factory     = new BrickFactory();
		queue       = new EventQueue();
		task2       = new HeartBeat();
		levelMgr    = new Level(task2);
		
		pianist     = new Pianist();
		listeners   = new HashSet<GameListener>();
		
		shapePic    = new Page(size * unit, size * unit);
		shapePicImg = new Page(size * unit, size * unit);
		nextShapePic = new Page(size * unit_s, size * unit_s);
		genShape();
		
		this.setName("app-" + count);
		count ++;
	}
	
	public void addGameListener(GameListener l)
	{
		listeners.add(l);
	}
	
	/**
	 * 重绘界面
	 * 
	 * 在执行 dispose() 之后，游戏实例与界面解除绑定，
	 * 所以重绘界面时需要检查窗口是否存在
	 */
	private void refreshUI()
	{
		if (view != null)
		{
			view.repaint();
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
			
			task2.cancel();
			
			for (GameListener l : listeners)
				l.onGamePause();
			
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
			
			task2.start();
			
			for (GameListener l : listeners)
				l.onGameResume();
			
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
	
	// XXX
	synchronized public Brick currentShape()
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
				levelMgr.addScore(6);
				pianist.ding4();
			}
			else if (rows == 3)
			{
				levelMgr.addScore(4);
				pianist.ding3();
			}
			else if (rows == 2)
			{
				levelMgr.addScore(2);
				pianist.ding2();
			}
			else if (rows == 1)
			{
				levelMgr.addScore(1);
				pianist.ding1();
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
		
		task2.cancel();
		
		for (GameListener l : listeners)
		{
			l.onGameOver();
		}
		
		refreshUI();

		// 播放结束音乐
		RealPlayer.execute(MusicEvent.GAME_OVER);
		
		throw new GameOverSig();
	}
	
	private void rotateShape()
	{
		Brick nextShape = factory.rotate(shape);
		
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
		return levelMgr.getScore();
	}
	
	synchronized public int getLevel()
	{
		return levelMgr.getLevel();
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
		
		
		status = Status.RUNNING;
		
		for (GameListener l : listeners)
		{
			l.onGameStart();
		}
		
		task2.start();
		
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
		
		// ================================
		// 游戏结束 - 清理一部分资源
		// ================================
		
		// 不解除与 win 的绑定. 
		// 游戏结束界面仍然可以在窗口显示
		
		// 停止定时器
		task2.cancel();
	}
}

