package game.ui;

import game.App;
import game.model.Command;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * 游戏视图
 * 
 * 游戏视图将游戏的运行结果绘制在屏幕上
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
	 * 清理当前游戏实例
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
	 * 创建一个新的游戏实例
	 * 
	 * 自动清理已存在的游戏实例
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
