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

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final Dimension size;

	public GamePanel()
	{
		int unit        = TetrisConstants.TILE_SIZE;
		int widthPixel  = TetrisConstants.SPACE_WIDTH * unit;
		int heightPixel = TetrisConstants.SPACE_HEIGHT_EX * unit;
		
		size = new Dimension(widthPixel, heightPixel);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return size;
	}
	
	private Window getWindow()
	{
		Container c = this.getTopLevelAncestor();

		if (c instanceof Window)
			return (Window) c;
		
		return null;
	}

	/**
	 * ���Ļ������� AWT-EventQueue-0 �߳�ֱ��ִ�е�
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		int unit = TetrisConstants.TILE_SIZE;
		
		Window win = getWindow();
		if (win == null)
			return;
		
		App app = win.getApp();
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(TetrisConstants.FONT);
		
		//g2.clearRect(0, 0, size.width, size.height);
		//g2.translate(0, 0);
		
		// ���Ʊ���
		int boxWidth  = TetrisConstants.SPACE_WIDTH;
		int boxHeight = TetrisConstants.SPACE_HEIGHT_EX;
		int edenHeight = boxHeight - TetrisConstants.SPACE_HEIGHT;
		g2.setColor(TetrisConstants.COLOR_SPACE);
		g2.fillRect(0, 0, boxWidth * unit, boxHeight * unit);

		if (app == null)
			return;
		
		
		
		
		Shape shape = app.currentShape();
		
		int ci = boxHeight - 1;
		
		// ���ƿռ�
		g2.drawImage(app.snapshot(), 0, 0, null);
		
		// �Ȼ��Ʒ��龵��
		g2.drawImage(app.getShapePicImg(), 
				shape.getX() * unit, 
				(ci - shape.getImageY() - shape.getMapSize() + 1) * unit, null);

		// �ٻ��Ʒ��鱾��
		g2.drawImage(app.getShapePic(), 
				shape.getX() * unit, 
				(ci - shape.getY() - shape.getMapSize() + 1) * unit, null);
		
		// ��������Ӱ
		g2.setColor(TetrisConstants.COLOR_EDEN);
		g2.fillRect(0, 0, boxWidth * unit, edenHeight * unit);

		
		// ��Ϸ״̬
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
