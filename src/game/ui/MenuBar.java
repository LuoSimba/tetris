package game.ui;

import game.sound.RealPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ���˵�
 */
public class MenuBar extends JMenuBar {
	
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
		JMenu menu = new JMenu("�ļ�");
		
		JMenuItem[] items = {
				new JMenuItem("����Ϸ"),
				new JMenuItem("������Ϸ"),
		};
		patchAdd(menu, items, listener);

		menu.addSeparator();
		
		JMenuItem[] items2 = {
				new JMenuItem("����"),
				new JMenuItem("�˳�"),
		};
		patchAdd(menu, items2, listener);
		
		this.add(menu);
		
		addMusicMenu();
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
}
