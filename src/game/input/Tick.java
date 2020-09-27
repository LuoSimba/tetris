package game.input;

import game.App;
import game.model.Command;
import game.model.EventQueue;

import java.util.Timer;
import java.util.TimerTask;

public class Tick {
	
	private App app;
	private Timer timer;
	private TimerTask task;
	
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
				
				if (app.isGameOver())
					return;
				
				EventQueue queue = app.getQueue();
				queue.offer(Command.DOWN);
			}
		};
		
		timer = new Timer();
		timer.schedule(task, 2000, 1000);
	}
}
