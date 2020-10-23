package game.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ����ʱ��
 * 
 * ��Ϊ��������
 */
public class Task {
	
	/**
	 * Executor -> ExecutorService -> ScheduledExecutorService
	 */
	private static ScheduledExecutorService inst = null;
	
	/**
	 * �򿪷���
	 */
	synchronized public static void open()
	{
		if (inst == null)
		{
			inst = Executors.newSingleThreadScheduledExecutor();
			// Executors.newScheduledThreadPool(1)
		}
	}
	
	/**
	 * �رշ���
	 */
	synchronized public static void close()
	{
		if (inst != null)
		{
			inst.shutdown();
			inst = null;
		}
	}
	
	/**
	 * ��Ӷ�ʱ��
	 * 
	 * ����ÿһ��ִ��һ��
	 */
	synchronized public static ScheduledFuture<?> addTick(Runnable r)
	{
		if (inst == null)
			return null;
		
		ScheduledFuture<?> future = 
			inst.scheduleAtFixedRate(r, 1, 1, TimeUnit.SECONDS);
		
		return future;
	}

	private Task()
	{
	}
}
