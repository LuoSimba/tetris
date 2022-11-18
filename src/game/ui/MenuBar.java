package game.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

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
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private ActionListener listener;

	public MenuBar()
	{
		this.listener = new MenuProc();
	}
}
