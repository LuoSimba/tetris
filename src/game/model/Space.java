package game.model;

import game.config.TetrisConstants;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * 坐标系的原点在左下角，X轴向右，Y轴向上
 * 
 * 最底下的一行为第 0 行，最顶行为第 19 行
 * 
 * 空间之上还有5行不可见的区域，作为新方块的出生区。
 * 不可见区域应该总是为空的，如果方块叠加到达不可见区域，
 * 则视为溢出，游戏结束。
 */
public class Space {
	
	private int[] dataA;
	private Page image;
	
	public Space()
	{
		int width = TetrisConstants.SPACE_WIDTH;
		int height = TetrisConstants.SPACE_HEIGHT_EX;
		int unit   = TetrisConstants.TILE_SIZE;
		
		dataA = new int[ height ];
		Arrays.fill(dataA, TetrisConstants.INIT_SPACE_ROW);
	
		image = new Page(width * unit, height * unit);
		
		redraw();
	}
	
	private void redraw() 
	{
		int width = TetrisConstants.SPACE_WIDTH;
		int height = TetrisConstants.SPACE_HEIGHT_EX;
		int offset = TetrisConstants.WALL_WIDTH;
		int unit   = TetrisConstants.TILE_SIZE;
		int ci     = height -1;
		
		image.clear();
		Graphics2D g2 = image.getContext();
		
		// draw
		g2.setColor(Color.RED);
		
		for (int y = 0; y < height; y ++)
		{
			int row = dataA[y];
			
			for (int x = 0; x < width; x ++)
			{
				int bitMask = 1 << (offset + x);
				
				if ((row & bitMask) != 0)
				{
					g2.fillRect(
							x * unit + 1, 
							(ci-y) * unit + 1, 
							unit - 2, 
							unit - 2);
				}
			}
		}
		
		g2.dispose();
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public boolean isConflictImage(Shape shape)
	{
		return isConflict(shape, true);
	}
	
	public boolean isConflict(Shape shape)
	{
		return isConflict(shape, false);
	}
	
	/**
	 * 是否碰撞
	 */
	private boolean isConflict(Shape shape, boolean bUseImage)
	{
		int[] raster = shape.getData();
		int begin    = bUseImage ? shape.getImageY() : shape.getY();
		
		for (int i = 0; i < raster.length; i ++)
		{
			int src = raster[i];
			int i2 = begin + i;
			
			int dst;
			
			// 如果行号为负，则已经超过空间底部
			if (i2 < 0)
				dst = 0xFFFFFFFF;
			// 如果行号越过了顶部，则认为是无限的空间
			else if (i2 >= TetrisConstants.SPACE_HEIGHT_EX)
				dst = 0x00000000;
			// 其他情况就获取空间的真实数据
			else
				dst = dataA[i2];
			
			if ((src & dst) != 0)
				return true;
		}
		
		return false;
	}
	
	/**
	 * 方块合并，因为方块的图片已经有现成的，所以
	 * 直接传入而不重新生成
	 */
	public void mergeShape(Shape shape, Page shapePic)
	{
		int unit = TetrisConstants.TILE_SIZE;
		int[] raster = shape.getData();
		int offset = shape.getY();
		int offset2 = offset + shape.getMapSize() -1;
		int ci = TetrisConstants.SPACE_HEIGHT_EX -1;
		
		for (int y = 0; y < raster.length; y ++)
		{
			int dst_pos = offset + y;
			
			if (dst_pos < 0)
				; // TODO
			else if (dst_pos < dataA.length)
				dataA[ dst_pos ] |= raster[y];
			else
				; // TODO
		}
		
		// 绘制图片
		Graphics2D g = image.getContext();
		g.drawImage(shapePic, 
				shape.getX() * unit, 
				(ci - offset2) * unit, 
				null);
	}
	
	/**
	 * 检查是否溢出
	 */
	public boolean isOverflow()
	{
		int start = TetrisConstants.SPACE_HEIGHT;
		int end   = TetrisConstants.SPACE_HEIGHT_EX;
		int bitMask = TetrisConstants.MASK_ROW_FULL;
		
		for (int i = start; i < end; i ++)
		{
			int row = dataA[ i ];
			if ((row & bitMask) != 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 消去行
	 */
	public int clearFullRows()
	{
		int nRowDel = 0;
		
		for (int i = 0; i < dataA.length; i ++)
		{
			while (isRowFull(dataA[i]))
			{
				nRowDel ++;
				deleteRow(i);
			}
		}
		
		return nRowDel;
	}
	
	/**
	 * 消去某一行
	 * 
	 * （同时处理界面）
	 * 
	 * 则更高的行会落下
	 */
	private void deleteRow(final int index)
	{
		int topIndex = dataA.length - 1;
		
		for (int i = index; i < topIndex; i ++)
		{
			dataA[ i ] = dataA[ i + 1];
		}
		
		// 新补充的一行必须也是初始数据，而不是 0x00000000
		dataA[topIndex] = TetrisConstants.INIT_SPACE_ROW;
		
		// ui
		int unit = TetrisConstants.TILE_SIZE;
		int ci = TetrisConstants.SPACE_HEIGHT_EX - 1;
		int width = TetrisConstants.SPACE_WIDTH;

		Graphics2D g = image.getContext();
		g.setComposite(Page.COMP_SRC);
		g.copyArea(
				0, 0, 
				width * unit, (ci - index) * unit,
				0, unit);
		g.setComposite(Page.COMP_CLR);
		g.fillRect(0, 0, width * unit, unit);
		g.dispose();
	}
	
	private boolean isRowFull(int row)
	{
		return (row & TetrisConstants.MASK_ROW_FULL) == TetrisConstants.MASK_ROW_FULL;
	}
}
