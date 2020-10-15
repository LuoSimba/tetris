package game.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 主菜单
 */
public class MenuBar extends JMenuBar {
	
	private class CommandProc implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			if (command == "帮助")
			{
				new HelpDialog();
			}
			else if (command == "退出")
			{
				System.exit(0);
			}
			else
			{
				System.out.println("菜单命令：" + command);
			}
		}
	};

	
	private static final long serialVersionUID = 1L;

	public MenuBar()
	{
		JMenu menu = new JMenu("文件");
		
		CommandProc listener = new CommandProc();
		
		JMenuItem[] items = {
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
