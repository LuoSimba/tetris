package game;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.signal.GameOverSignal;
import game.sound.RealPlayer;
import game.ui.Window;

public class Boot {
	
	private static Window win;
	
	private static void init()
	{
		try {
			String uiStyle = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(uiStyle);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		win = Window.getInstance();
		
		RealPlayer player = new RealPlayer();
		player.play();
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

