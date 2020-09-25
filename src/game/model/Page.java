package game.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * default composite = SRC_OVER
 */
public class Page extends BufferedImage {

	public Page(int width, int height)
	{
		super(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public Graphics2D getContext()
	{
		return this.createGraphics();
	}
}
