package game.ui;

import game.sound.RealPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 主菜单
 */
public class MenuBar extends JMenuBar {
	
	/**
	 * 处理背景音乐菜单
	 */
	private class MenuProc_Music implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if (cmd == "打开")
			{
				RealPlayer.open();
			}
			else if (cmd == "关闭")
			{
				RealPlayer.close();
			}
			else 
			{
				System.out.println("处理背景音乐菜单命令：" + cmd);
			}
		}
	}
	
	private static final long serialVersionUID = 1L;

	public MenuBar(ActionListener listener)
	{
		JMenu menu = new JMenu("文件");
		
		JMenuItem[] items = {
				new JMenuItem("新游戏"),
				new JMenuItem("结束游戏"),
		};
		patchAdd(menu, items, listener);

		menu.addSeparator();
		
		JMenuItem[] items2 = {
				new JMenuItem("帮助"),
				new JMenuItem("退出"),
		};
		patchAdd(menu, items2, listener);
		
		this.add(menu);
		
		addMusicMenu();
	}
	
	/**
	 * 背景音乐菜单
	 */
	private void addMusicMenu()
	{
		JMenu menu = new JMenu("背景音乐");
		
		ActionListener al = new MenuProc_Music();
		
		JMenuItem[] items = {
				new JMenuItem("打开"),
				new JMenuItem("关闭"),
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
