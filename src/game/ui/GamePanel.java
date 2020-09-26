package game.ui;

import game.App;
import game.config.TetrisConstants;
import game.model.Shape;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final int padding = 30;
	private final Dimension size;
	private final BackgroundImage bgImg;

	public GamePanel()
	{
		int unit        = TetrisConstants.TILE_SIZE;
		int widthPixel  = TetrisConstants.SPACE_WIDTH * unit + padding * 2;
		int heightPixel = TetrisConstants.SPACE_HEIGHT_EX * unit + padding * 2;
		
		size = new Dimension(widthPixel, heightPixel);
		
		bgImg         = new BackgroundImage();
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return size;
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		App app = App.getInstance();
		Shape shape = app.currentShape();
		
		int unit            = TetrisConstants.TILE_SIZE;
		int spaceWidth      = TetrisConstants.SPACE_WIDTH;
		int spaceRealHeight = TetrisConstants.SPACE_HEIGHT_EX;
		int offset          = TetrisConstants.WALL_WIDTH;
		int ci = spaceRealHeight - 1;
		
		//g2.clearRect(0, 0, size.width, size.height);
		g2.translate(padding, padding);

		// 绘制背景
		g2.drawImage(bgImg, 0, 0, null);
		
		
		// 绘制空间
		g2.drawImage(app.snapshot(), 0, 0, null);
		
		// 先绘制方块镜像
		g2.drawImage(app.getShapePicImg(), 
				shape.getX() * unit, 
				(ci - shape.getImageY() - shape.getMapSize() + 1) * unit, null);

		// 再绘制方块本体
		g2.drawImage(app.getShapePic(), 
				shape.getX() * unit, 
				(ci - shape.getY() - shape.getMapSize() + 1) * unit, null);
		
		// 游戏状态
		if (app.isPaused())
		{
			g2.setColor(Color.GREEN);
			g2.drawString("PAUSE", 100, 100);
		}
	}
}
