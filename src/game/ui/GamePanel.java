package game.ui;

import game.App;
import game.config.TetrisConstants;
import game.model.Skin;
import game.model.Brick;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public GamePanel()
	{
		int unit        = TetrisConstants.TILE_SIZE;
		int widthPixel  = TetrisConstants.SPACE_WIDTH * unit + 12;
		int heightPixel = TetrisConstants.SPACE_HEIGHT_EX * unit + 12;

		this.setPreferredSize(new Dimension(widthPixel, heightPixel));
	}
	
	private GameView getView()
	{
		//Container c = this.getTopLevelAncestor();
		
		Container c = this.getParent();

		if (c instanceof GameView)
			return (GameView) c;
		
		return null;
	}

	/**
	 * 面板的绘制是由 AWT-EventQueue-0 线程直接执行的
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		final Skin cs = Skin.getDefaultSkin();
		
		int unit = TetrisConstants.TILE_SIZE;
		
		GameView view = getView();
		if (view == null)
			return;
		
		
		App app = view.getApp();
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(TetrisConstants.FONT);
		
		//g2.clearRect(0, 0, size.width, size.height);
		//g2.translate(0, 0);
		
		// 绘制背景
		g2.setColor(cs.getSpaceColor());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2.translate(3, 3);
		// 绘制边框
		g2.setColor(cs.getBorderColor());
		Stroke pen = new BasicStroke(2F);
		g2.setStroke(pen);
		g2.drawRect(0, 0, this.getWidth() - 6, this.getHeight() - 6);

		// 绘制主体
		g2.translate(3, 3);
		int boxWidth  = TetrisConstants.SPACE_WIDTH;
		int boxHeight = TetrisConstants.SPACE_HEIGHT_EX;
		int edenHeight = boxHeight - TetrisConstants.SPACE_HEIGHT;

		if (app == null) {
			g2.setColor(Color.GREEN);
			g2.drawString("press N to start", 80, 100);
			return;
		}
		
		
		
		
		Brick shape = app.currentShape();
		
		int ci = boxHeight - 1;
		
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
		
		// 出生区阴影
		g2.setColor(cs.getEdenColor());
		g2.fillRect(0, 0, boxWidth * unit, edenHeight * unit);

		
		// 游戏状态
		if (app.isGameOver())
		{
			g2.setColor(Color.GREEN);
			g2.drawString("GAME OVER", 100, 100);
		}
		else if (app.isPaused())
		{
			g2.setColor(Color.GREEN);
			g2.drawString("PAUSE", 100, 100);
		}
	}
}
