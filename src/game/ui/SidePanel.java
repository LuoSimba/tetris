package game.ui;

import game.App;
import game.config.TetrisConstants;
import game.model.ColorScheme;
import game.model.Shape;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SidePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Dimension size;

	public SidePanel()
	{
		size = new Dimension(150, 400);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	
	private GameView getView()
	{
		Container c = this.getParent();

		if (c instanceof GameView)
			return (GameView) c;
		
		return null;
	}

	/**
	 * ���Ʊ���
	 * 
	 * �� AWT-EventQueue-0 ֱ��ִ��
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		final ColorScheme cs = ColorScheme.getDefaultColorScheme();
		
		Graphics2D g2 = (Graphics2D) g;
		int width = this.getWidth();
		int height = this.getHeight();
		
		
		// ���Ʊ���ɫ
		// set background color
		g2.setBackground(cs.getSideBackgroundColor());
		g2.clearRect(0, 0, width, height);
		
		
		// ��ȡ����
		// ���û����ӵ������У����ټ�������
		GameView view = getView();
		if (view == null)
			return;

		// ��ȡ��Ϸʵ��
		// ���û����Ϸʵ����Ҳ����Ҫ��������
		App app = view.getApp();
		if (app == null)
			return;
		
		
		Shape shape = app.currentShape();
		
		
		g2.setColor(Color.RED);
		g2.setFont(TetrisConstants.FONT);
		g2.drawString("x=" + shape.getX(), 10, 50);
		g2.drawString("y=" + shape.getY() + ", " + shape.getImageY(), 10, 70);
		
		g2.drawString("�÷�", 10, 110);
		g2.drawString("" + app.getScore(), 10, 130);
		
		g2.drawString("�ȼ�", 10, 170);
		g2.drawString("" + app.getLevel(), 10, 190);
		
		// ��һ������Ԥ��ͼ
		g2.drawImage(app.getNextShapePic(), 10, 200, null);
	}
	
}
