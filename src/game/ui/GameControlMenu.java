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
	 * ��ͣ��Ϸ
	 */
	final private JCheckBoxMenuItem itemPauseGame;
	/**
	 * ������Ϸ
	 */
	final private JMenuItem itemDispose;
	
	final GameView view;

	public GameControlMenu(GameView view)
	{
		super(view.getName());
		
		this.view = view;
		
		itemNewGame   = new JMenuItem("����Ϸ");
		itemPauseGame = new JCheckBoxMenuItem("��ͣ");
		itemDispose   = new JMenuItem("������Ϸ");
		
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
		
		if (command == "����Ϸ")
		{
			App app = view.createNewGame();
			app.addGameListener(this);
			// ������ʼ��Ϸ
			app.start();
		}
		else if (command == "��ͣ")
		{
			view.putCommand(Command.PAUSE);
		}
		else if (command == "������Ϸ")
		{
			view.killGame();
			view.repaint();
		}
	}
}
