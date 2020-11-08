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
	 * 绘制边栏
	 * 
	 * 由 AWT-EventQueue-0 直接执行
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		final ColorScheme cs = ColorScheme.getDefaultColorScheme();
		
		Graphics2D g2 = (Graphics2D) g;
		int width = this.getWidth();
		int height = this.getHeight();
		
		
		// 绘制背景色
		// set background color
		g2.setBackground(cs.getSideBackgroundColor());
		g2.clearRect(0, 0, width, height);
		
		
		// 获取窗口
		// 如果没有添加到窗口中，则不再继续绘制
		GameView view = getView();
		if (view == null)
			return;

		// 获取游戏实例
		// 如果没有游戏实例，也不需要继续绘制
		App app = view.getApp();
		if (app == null)
			return;
		
		
		Shape shape = app.currentShape();
		
		
		g2.setColor(Color.RED);
		g2.setFont(TetrisConstants.FONT);
		g2.drawString("x=" + shape.getX(), 10, 50);
		g2.drawString("y=" + shape.getY() + ", " + shape.getImageY(), 10, 70);
		
		g2.drawString("得分", 10, 110);
		g2.drawString("" + app.getScore(), 10, 130);
		
		g2.drawString("等级", 10, 170);
		g2.drawString("" + app.getLevel(), 10, 190);
		
		// 下一个方块预览图
		g2.drawImage(app.getNextShapePic(), 10, 200, null);
	}
	
}
