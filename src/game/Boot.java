package game;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		
		RealPlayer.getInstance().play();
	}

	public static void main(String[] args)
	{
		init();
		App app = new App(win);
		Window.getInstance().play();
	}
}

//Integer.toBinaryString(-1);

