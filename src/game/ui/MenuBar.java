package game.ui;

import game.sound.RealPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 主菜单
 * 
 * 菜单参与监听游戏的状态变化
 */
public class MenuBar extends JMenuBar {
	
	/**
	 * 处理菜单命令
	 */
	private class MenuProc implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			Object src = e.getSource();

			if (cmd == "背景音乐")
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
			else if (cmd == "帮助")
			{
				new HelpDialog(win);
			}
			else if (cmd == "")
			{
				win.dispose();
			}
			else
			{
				System.out.println("菜单命令：" + cmd);
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
		
		JMenu menu = new JMenu("文件");
		
		add0(menu, new JCheckBoxMenuItem("背景音乐"), listener);
		add0(menu, new JMenuItem("帮助"), listener);
		menu.addSeparator();
		add0(menu, new JMenuItem("退出"), listener);
		
		this.add(menu);
	}
	
	private void add0(JMenu menu, JMenuItem item, ActionListener al)
	{
		item.addActionListener(al);
		menu.add(item);
	}
}
