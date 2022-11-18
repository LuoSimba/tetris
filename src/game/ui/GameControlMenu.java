package game.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.App;
import game.model.Command;
import game.model.GameListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 控制游戏行为的一组菜单
 */
public class GameControlMenu extends JMenu
implements GameListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 新游戏
	 */
	final private JMenuItem itemNewGame;
	/**
	 * 结束游戏
	 */
	final private JMenuItem itemDispose;
	
	final GameView view;

	public GameControlMenu(GameView view)
	{
		super("游戏");
		
		this.view = view;
		
		itemNewGame   = new JMenuItem("新游戏");
		itemDispose   = new JMenuItem("结束游戏");
		
		itemDispose.setEnabled(false);
		
		itemNewGame.addActionListener(this);
		itemDispose.addActionListener(this);
		
		this.add(itemNewGame);
		this.add(itemDispose);
	}

	@Override
	public void onDispose() {
		
		itemDispose.setEnabled(false);
	}

	@Override
	public void onGameOver() {
	}

	@Override
	public void onGamePause() {
	}

	@Override
	public void onGameResume() {
	}

	@Override
	public void onGameStart() {
		
		itemDispose.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		String command = e.getActionCommand();
		
		if (command == "新游戏")
		{
			App app = view.createNewGame();
			app.addGameListener(this);
			// 立即开始游戏
			app.start();
		}
		else if (command == "结束游戏")
		{
			view.killGame();
			view.repaint();
		}
	}
}
