package game.ui;

import javax.swing.JDialog;

/**
 * 帮助 - 对话框
 */
public class HelpDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public HelpDialog()
	{
		super(Window.getInstance(), "帮助", true);
		
		java.awt.Window owner = this.getOwner();

		// 先设置对话框大小
		this.setSize(600, 400);
		// 再重新定位在 Window 之上
		this.setLocationRelativeTo(owner);
		
		this.setVisible(true);
	}
}
