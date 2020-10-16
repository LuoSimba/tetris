package game.ui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ���˵�
 */
public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;

	public MenuBar(ActionListener listener)
	{
		JMenu menu = new JMenu("�ļ�");
		
		JMenuItem[] items = {
				new JMenuItem("����Ϸ"),
				new JMenuItem("����"),
				new JMenuItem("�˳�"),
		};
		
		for (int i = 0; i < items.length; i ++)
		{
			items[i].addActionListener(listener);
			menu.add(items[i]);
		}
		
		this.add(menu);
	}
}
