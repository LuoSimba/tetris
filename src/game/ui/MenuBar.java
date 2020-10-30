package game.ui;

import game.model.GameListener;
import game.sound.RealPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ���˵�
 * 
 * �˵����������Ϸ��״̬�仯
 */
public class MenuBar extends JMenuBar 
implements GameListener {
	
	final private JCheckBoxMenuItem menuPause;
	final private JMenuItem menuDispose;
	
	/**
	 * ���������ֲ˵�
	 */
	private class MenuProc_Music implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if (cmd == "��")
			{
				RealPlayer.open();
			}
			else if (cmd == "�ر�")
			{
				RealPlayer.close();
			}
			else 
			{
				System.out.println("���������ֲ˵����" + cmd);
			}
		}
	}
	
	private static final long serialVersionUID = 1L;

	public MenuBar(ActionListener listener)
	{
		menuPause = new JCheckBoxMenuItem("��ͣ");
		menuDispose = new JMenuItem("������Ϸ");
		
		menuPause.setEnabled(false);
		menuDispose.setEnabled(false);



		JMenu menu = new JMenu("�ļ�");
		//menu.addSeparator();
		
		JMenuItem[] items2 = {
				new JMenuItem("����"),
				new JMenuItem("�˳�"),
		};
		patchAdd(menu, items2, listener);
		
		this.add(menu);
		
		addGameMenu(listener);
		
		addMusicMenu();
	}
	
	/**
	 * ��Ϸ�˵�
	 */
	private void addGameMenu(ActionListener listener)
	{
		JMenu menu = new JMenu("��Ϸ");
		
		JMenuItem[] items = {
				new JMenuItem("����Ϸ"),
				menuPause,
				menuDispose,
		};
		patchAdd(menu, items, listener);
		
		this.add(menu);
	}
	
	
	/**
	 * �������ֲ˵�
	 */
	private void addMusicMenu()
	{
		JMenu menu = new JMenu("��������");
		
		ActionListener al = new MenuProc_Music();
		
		JMenuItem[] items = {
				new JMenuItem("��"),
				new JMenuItem("�ر�"),
		};
		patchAdd(menu, items, al);
		
		this.add(menu);
	}
	
	private void patchAdd(JMenu menu, JMenuItem[] items, ActionListener al)
	{
		for (int i = 0; i < items.length; i ++)
		{
			items[i].addActionListener(al);
			menu.add(items[i]);
		}
	}

	@Override
	public void onGamePause() {
		menuPause.setSelected(true);
	}

	@Override
	public void onGameResume() {
		menuPause.setSelected(false);
	}

	@Override
	public void onDispose() {
		menuPause.setSelected(false);
		menuPause.setEnabled(false);
		
		menuDispose.setEnabled(false);
	}

	@Override
	public void onGameOver() {
		menuPause.setSelected(false);
		menuPause.setEnabled(false);
	}

	@Override
	public void onGameStart() {
		
		menuPause.setSelected(false);
		menuPause.setEnabled(true);
		
		menuDispose.setEnabled(true);
	}
}
