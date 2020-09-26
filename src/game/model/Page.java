package game.model;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * default composite = SRC_OVER
 */
public class Page extends BufferedImage {

	public static final Composite COMP_CLR = AlphaComposite.getInstance(AlphaComposite.CLEAR);
	public static final Composite COMP_SRC = AlphaComposite.getInstance(AlphaComposite.SRC);
	
	public Page(int width, int height)
	{
		super(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public Graphics2D getContext()
	{
		return this.createGraphics();
	}

	/**
	 * Çå³ýÍ¼Ïñ
	 */
	public void clear()
	{
		Graphics2D g = this.getContext();
		g.setComposite(COMP_CLR);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.dispose();
	}
}
