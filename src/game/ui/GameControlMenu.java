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
	 * 暂停游戏
	 */
	final private JCheckBoxMenuItem itemPauseGame;
	/**
	 * 结束游戏
	 */
	final private JMenuItem itemDispose;
	
	final GameView view;

	public GameControlMenu(GameView view)
	{
		super(view.getName());
		
		this.view = view;
		
		itemNewGame   = new JMenuItem("新游戏");
		itemPauseGame = new JCheckBoxMenuItem("暂停");
		itemDispose   = new JMenuItem("结束游戏");
		
		itemPauseGame.setEnabled(false);
		itemDispose.setEnabled(false);
		
		itemNewGame.addActionListener(this);
		itemPauseGame.addActionListener(this);
		itemDispose.addActionListener(this);
		
		this.add(itemNewGame);
		this.add(itemPauseGame);
		this.add(itemDispose);
	}

	@Override
	public void onDispose() {
		itemPauseGame.setSelected(false);
		itemPauseGame.setEnabled(false);
		
		itemDispose.setEnabled(false);
	}

	@Override
	public void onGameOver() {
		itemPauseGame.setSelected(false);
		itemPauseGame.setEnabled(false);
	}

	@Override
	public void onGamePause() {
		itemPauseGame.setSelected(true);
	}

	@Override
	public void onGameResume() {
		itemPauseGame.setSelected(false);
	}

	@Override
	public void onGameStart() {
		
		itemPauseGame.setSelected(false);
		itemPauseGame.setEnabled(true);
		
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
		else if (command == "暂停")
		{
			view.putCommand(Command.PAUSE);
		}
		else if (command == "结束游戏")
		{
			view.killGame();
			view.repaint();
		}
	}
}
