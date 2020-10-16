package game.input;

import game.App;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 控制游戏的心跳
 */
public class Tick {
	
	private Timer timer;
	private TimerTask task;
	private App app;
	
	public Tick(App app)
	{
		this.app = app;
	}
	
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
		
		
		task = new TimerTask() {

			@Override
			public void run() {
				app.beat();
			}
		};
		
		timer = new Timer();
		timer.schedule(task, 2000, 1000);
	}
}
