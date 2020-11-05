package game.model;

/**
 * 等级管理
 */
public class Level {

	private int level;
	private int score;
	
	/**
	 * 达到下一个等级的分数值
	 */
	private int goal;
	
	private Task task;
	
	/**
	 * 心跳的速度(毫秒值)
	 */
	private int speedMS;
	
	public Level(Task task)
	{
		// 初始等级从 1 开始
		level = 1;
		score = 0;
		goal = 20;
		speedMS = 1000;
		
		this.task = task;
	}
	
	/**
	 * 得分
	 */
	synchronized public void addScore(int value)
	{
		score += value;
		
		checkLevelUp();
	}
	
	/**
	 * 处理关卡升级
	 * 
	 * 在计分变动的时候检查
	 */
	private void checkLevelUp()
	{
		if (score >= goal)
		{
			level ++;
			
			speedMS -= 20;
			
			// 速度不能超过极限
			if (speedMS <= 0)
				speedMS = 1;
			
			// TODO: debug
			//System.out.println("speed: " + speedMS);
			
			// XXX: 加快游戏心跳
			if (task != null && task.isRunning())
			{
				task.cancel();
				task.start();
			}
			
			// TODO: 下一次升级的门槛
			goal += 20;
		}
	}
	
	synchronized public int getScore()
	{
		return score;
	}
	
	synchronized public int getLevel()
	{
		return level;
	}
	
	synchronized public int getSpeed()
	{
		return speedMS;
	}
}
