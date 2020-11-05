package game.model;

import java.util.concurrent.ScheduledFuture;

public interface Task extends Runnable {

	/**
	 * ����ļ��������
	 */
	int getInterval();
	
	/**
	 * ��������
	 */
	void cancel();
	
	void setHandle(ScheduledFuture<?> future);
	
	boolean isRunning();
	
	/**
	 * ��ʼ����
	 */
	void start();
}
