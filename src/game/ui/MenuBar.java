package game.ui;

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
public class MenuBar extends JMenuBar {
	
	/**
	 * ����˵�����
	 */
	private class MenuProc implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			Object src = e.getSource();

			if (cmd == "��������")
			{
				JCheckBoxMenuItem item = (JCheckBoxMenuItem) src;
				
				if (item.isSelected())
				{
					RealPlayer.open();
				}
				else
				{
					RealPlayer.close();
				}
			}
			else if (cmd == "����")
			{
				new HelpDialog(win);
			}
			else if (cmd == "")
			{
				win.dispose();
			}
			else
			{
				System.out.println("�˵����" + cmd);
			}
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private Window win;
	
	private ActionListener listener;

	public MenuBar(Window win)
	{
		this.win = win;
		this.listener = new MenuProc();
		
		JMenu menu = new JMenu("�ļ�");
		
		add0(menu, new JCheckBoxMenuItem("��������"), listener);
		add0(menu, new JMenuItem("����"), listener);
		menu.addSeparator();
		add0(menu, new JMenuItem("�˳�"), listener);
		
		this.add(menu);
	}
	
	private void add0(JMenu menu, JMenuItem item, ActionListener al)
	{
		item.addActionListener(al);
		menu.add(item);
	}
}
