package game.ui;

import game.config.TetrisConstants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 背景图
 * 
 * 创建后就不会改变
 */
public class BackgroundImage extends BufferedImage {

	public BackgroundImage()
	{
		super(
				TetrisConstants.TILE_SIZE * TetrisConstants.SPACE_WIDTH, 
				TetrisConstants.TILE_SIZE * TetrisConstants.SPACE_HEIGHT_EX, 
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = this.getGraphics();
		
		int height = TetrisConstants.SPACE_HEIGHT_EX;
		int edenSize = height - TetrisConstants.SPACE_HEIGHT;
		int unit   = TetrisConstants.TILE_SIZE;
		
		g.setColor(TetrisConstants.COLOR_SPACE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(TetrisConstants.COLOR_EDEN);
		g.fillRect(0, 0, this.getWidth(), edenSize * unit);
	}
}
