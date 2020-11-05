package game.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 管理定时器
 * 
 * 作为服务运行
 */
public class TaskService {
	
	/**
	 * Executor -> ExecutorService -> ScheduledExecutorService
	 */
	private static ScheduledExecutorService inst = null;
	
	/**
	 * 打开服务
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
	 * 关闭服务
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
	 * 添加定时器
	 * 
	 * 间隔毫秒数执行
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
