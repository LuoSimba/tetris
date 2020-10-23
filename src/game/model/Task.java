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
public class Task {
	
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
	 * 总是每一秒执行一次
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
