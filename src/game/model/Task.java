package game.model;

import java.util.concurrent.ScheduledFuture;

public interface Task extends Runnable {

	/**
	 * 任务的间隔毫秒数
	 */
	int getInterval();
	
	/**
	 * 撤销任务
	 */
	void cancel();
	
	void setHandle(ScheduledFuture<?> future);
	
	boolean isRunning();
	
	/**
	 * 开始任务
	 */
	void start();
}
