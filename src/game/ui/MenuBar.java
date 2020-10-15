package game.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ���˵�
 */
public class MenuBar extends JMenuBar {
	
	private class CommandProc implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			if (command == "�˳�")
			{
				System.exit(0);
			}
			else
			{
				System.out.println("�˵����" + command);
			}
		}
	};

	
	private static final long serialVersionUID = 1L;

	public MenuBar()
	{
		JMenu menu = new JMenu("�ļ�");
		
		JMenuItem item = new JMenuItem("�˳�");
		
		menu.add(item);
		
		item.addActionListener(new CommandProc());
		
		this.add(menu);
	}
}
