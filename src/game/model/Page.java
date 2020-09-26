package game.model;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * default composite = SRC_OVER
 */
public class Page extends BufferedImage {

	private static final Composite comp = AlphaComposite.getInstance(AlphaComposite.CLEAR);
	
	public Page(int width, int height)
	{
		super(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public Graphics2D getContext()
	{
		return this.createGraphics();
	}

	/**
	 * ���ͼ��
	 */
	public void clear()
	{
		Graphics2D g = this.getContext();
		g.setComposite(comp);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.dispose();
	}
}
