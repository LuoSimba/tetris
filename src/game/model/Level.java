package game.model;

/**
 * �ȼ�����
 */
public class Level {

	private int level;
	private int score;
	
	/**
	 * �ﵽ��һ���ȼ��ķ���ֵ
	 */
	private int goal;
	
	private Task task;
	
	/**
	 * �������ٶ�(����ֵ)
	 */
	private int speedMS;
	
	public Level(Task task)
	{
		// ��ʼ�ȼ��� 1 ��ʼ
		level = 1;
		score = 0;
		goal = 20;
		speedMS = 1000;
		
		this.task = task;
	}
	
	/**
	 * �÷�
	 */
	synchronized public void addScore(int value)
	{
		score += value;
		
		checkLevelUp();
	}
	
	/**
	 * ����ؿ�����
	 * 
	 * �ڼƷֱ䶯��ʱ����
	 */
	private void checkLevelUp()
	{
		if (score >= goal)
		{
			level ++;
			
			speedMS -= 20;
			
			// �ٶȲ��ܳ�������
			if (speedMS <= 0)
				speedMS = 1;
			
			// TODO: debug
			//System.out.println("speed: " + speedMS);
			
			// XXX: �ӿ���Ϸ����
			if (task != null && task.isRunning())
			{
				task.cancel();
				task.start();
			}
			
			// TODO: ��һ���������ż�
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
