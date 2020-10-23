package game;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.model.Task;
import game.ui.Window;

public class Boot {
	
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
		
		Task.open();
	}

	public static void main(String[] args)
	{
		init();
		
		Window.createWindow();
	}
}

//Integer.toBinaryString(-1);

