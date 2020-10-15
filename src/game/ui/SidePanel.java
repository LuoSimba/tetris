package game.ui;

import game.App;
import game.config.TetrisConstants;
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
		size = new Dimension(200, 200);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	
	private Window getWindow()
	{
		Container c = this.getTopLevelAncestor();

		if (c instanceof Window)
			return (Window) c;
		
		return null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Window win = getWindow();
		if (win == null)
			return;
		
		App app = win.getApp();
		
		Graphics2D g2 = (Graphics2D) g;
		int width = this.getWidth();
		int height = this.getHeight();
		
		// set background color
		g2.setBackground(TetrisConstants.COLOR_SIDE_BG);
		g2.clearRect(0, 0, width, height);
		
		if (app == null)
			return;
		
		
		
		
		Shape shape = app.currentShape();
		
		
		g2.setColor(Color.RED);
		g2.setFont(TetrisConstants.FONT);
		g2.drawString("x=" + shape.getX(), 10, 50);
		g2.drawString("y=" + shape.getY() + ", " + shape.getImageY(), 10, 70);
		
		g2.drawString("得分", 10, 110);
		g2.drawString("" + app.getScore(), 10, 130);
		
		// 下一个方块预览图
		g2.drawImage(app.getNextShapePic(), 10, 200, null);
	}
	
}
