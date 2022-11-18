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
 * ������Ϸ��Ϊ��һ��˵�
 */
public class GameControlMenu extends JMenu
implements GameListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ����Ϸ
	 */
	final private JMenuItem itemNewGame;
	/**
	 * ������Ϸ
	 */
	final private JMenuItem itemDispose;
	
	final GameView view;

	public GameControlMenu(GameView view)
	{
		super("��Ϸ");
		
		this.view = view;
		
		itemNewGame   = new JMenuItem("����Ϸ");
		itemDispose   = new JMenuItem("������Ϸ");
		
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
		
		if (command == "����Ϸ")
		{
			App app = view.createNewGame();
			app.addGameListener(this);
			// ������ʼ��Ϸ
			app.start();
		}
		else if (command == "������Ϸ")
		{
			view.killGame();
			view.repaint();
		}
	}
}
