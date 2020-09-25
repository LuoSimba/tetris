package game.input;

import game.App;
import game.model.Command;

import java.util.Timer;
import java.util.TimerTask;

public class Tick {

	private Timer timer;
	
	private TimerTask task;
	
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
				
				try {
					App.getInstance().getQueue().put(Command.DOWN);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		timer = new Timer();
		timer.schedule(task, 2000, 1000);
	}
}
