package game.ui;

import game.App;
import game.model.Shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SidePanel extends JPanel {
	
	private Dimension size;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Font font = new Font("monospace", Font.PLAIN, 20);
	
	public SidePanel()
	{
		size = new Dimension(100, 100);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		App app = App.getInstance();
		Shape shape = app.currentShape();
		//super.paintComponent(g);
		
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		g2.clearRect(0, 0, width, height);
		g2.fillRect(0, 0, width, height);
		
		g2.setColor(Color.RED);
		g2.setFont(font);
		g2.drawString("x=" + shape.getX(), 10, 50);
		g2.drawString("y=" + shape.getY() + ", " + shape.getImageY(), 10, 70);
		g2.drawString("index=" + shape.getIndex(), 10, 90);
		g2.drawString("rows=" + app.getRowCount(), 10, 110);
		
		// 下一个方块预览图
		g2.drawImage(app.getNextShapePic(), 10, 200, null);
	}
	
}
