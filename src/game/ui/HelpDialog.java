package game.ui;

import javax.swing.JDialog;

/**
 * ���� - �Ի���
 */
public class HelpDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public HelpDialog()
	{
		super(Window.getInstance(), "����", true);
		
		java.awt.Window owner = this.getOwner();

		// �����öԻ����С
		this.setSize(600, 400);
		// �����¶�λ�� Window ֮��
		this.setLocationRelativeTo(owner);
		
		this.setVisible(true);
	}
}
