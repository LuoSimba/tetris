package game.ui;

import game.App;
import game.model.Command;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * ��Ϸ��ͼ
 * 
 * ��Ϸ��ͼ����Ϸ�����н����������Ļ��
 */
public class GameView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	private App app;

	
	public GameView()
	{
		app = null;
		
		this.setLayout(new BorderLayout());
		
		this.add(new GamePanel());
		
		this.add(new SidePanel(), BorderLayout.EAST);
	}
	
	/**
	 * ����ǰ��Ϸʵ��
	 */
	synchronized public void killGame()
	{
		if (app != null)
		{
			app.dispose();
			
			app = null;
		}
	}
	
	synchronized public App getApp()
	{
		return app;
	}
	
	/**
	 * ����һ���µ���Ϸʵ��
	 * 
	 * �Զ������Ѵ��ڵ���Ϸʵ��
	 */
	synchronized public App createNewGame()
	{
		killGame();
		
		app = new App(this);
		
		return app;
	}
	
	synchronized public void putCommand(Command cmd)
	{
		if (app == null)
			return;
		
		if (app.isGameOver())
			return;
		
		app.putCommand(cmd);
	}
}
