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
	 * ���Ļ������� AWT-EventQueue-0 �߳�ֱ��ִ�е�
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
		
		// ���Ʊ���
		g2.setColor(cs.getSpaceColor());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2.translate(3, 3);
		// ���Ʊ߿�
		g2.setColor(cs.getBorderColor());
		Stroke pen = new BasicStroke(2F);
		g2.setStroke(pen);
		g2.drawRect(0, 0, this.getWidth() - 6, this.getHeight() - 6);

		// ��������
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
		g2.setColor(cs.getEdenColor());
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
