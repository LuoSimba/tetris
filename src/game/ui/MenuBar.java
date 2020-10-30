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
 * 主菜单
 * 
 * 菜单参与监听游戏的状态变化
 */
public class MenuBar extends JMenuBar 
implements GameListener {
	
	final private JCheckBoxMenuItem menuPause;
	final private JMenuItem menuDispose;
	
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
		menuPause = new JCheckBoxMenuItem("暂停");
		menuDispose = new JMenuItem("结束游戏");
		
		menuPause.setEnabled(false);
		menuDispose.setEnabled(false);



		JMenu menu = new JMenu("文件");
		//menu.addSeparator();
		
		JMenuItem[] items2 = {
				new JMenuItem("帮助"),
				new JMenuItem("退出"),
		};
		patchAdd(menu, items2, listener);
		
		this.add(menu);
		
		addGameMenu(listener);
		
		addMusicMenu();
	}
	
	/**
	 * 游戏菜单
	 */
	private void addGameMenu(ActionListener listener)
	{
		JMenu menu = new JMenu("游戏");
		
		JMenuItem[] items = {
				new JMenuItem("新游戏"),
				menuPause,
				menuDispose,
		};
		patchAdd(menu, items, listener);
		
		this.add(menu);
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
