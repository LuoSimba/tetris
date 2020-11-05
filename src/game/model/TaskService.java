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
public class TaskService {
	
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
	 * ���������ִ��
	 */
	synchronized public static void addTick(Task task)
	{
		if (inst == null)
			return;
		
		int ms = task.getInterval();
		
		ScheduledFuture<?> future = 
			inst.scheduleAtFixedRate(task, ms, ms, TimeUnit.MILLISECONDS);
		
		task.setHandle(future);
	}

	private TaskService()
	{
	}
}
