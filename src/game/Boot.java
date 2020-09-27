package game;

import game.signal.GameOverSignal;
import game.ui.Window;

public class Boot {
	
	private static Window win;
	
	private static void init()
	{
		win = Window.getInstance();
	}

	public static void main(String[] args)
	{
		init();
		
		App app = new App(win);
		
		try {
			app.play();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameOverSignal e) {
			// ”Œœ∑Ω· ¯
			// do nothing
		}
			
		//App.dispose();
	}
}

//Integer.toBinaryString(-1);

