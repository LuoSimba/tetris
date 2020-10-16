package game.ui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 主菜单
 */
public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;

	public MenuBar(ActionListener listener)
	{
		JMenu menu = new JMenu("文件");
		
		JMenuItem[] items = {
				new JMenuItem("新游戏"),
				new JMenuItem("帮助"),
				new JMenuItem("退出"),
		};
		
		for (int i = 0; i < items.length; i ++)
		{
			items[i].addActionListener(listener);
			menu.add(items[i]);
		}
		
		this.add(menu);
	}
}
