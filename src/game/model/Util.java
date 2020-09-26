package game.model;

public class Util {

	public static int random(int max)
	{
		double i = Math.random();
		
		i *= max;
		
		return (int)i;
	}
}
