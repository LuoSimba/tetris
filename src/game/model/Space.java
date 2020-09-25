package game.model;

import game.config.TetrisConstants;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * ����ϵ��ԭ�������½ǣ�X�����ң�Y������
 * 
 * ����µ�һ��Ϊ�� 0 �У����Ϊ�� 19 ��
 * 
 * �ռ�֮�ϻ���5�в��ɼ���������Ϊ�·���ĳ�������
 * ���ɼ�����Ӧ������Ϊ�յģ����������ӵ��ﲻ�ɼ�����
 * ����Ϊ�������Ϸ������
 */
public class Space {
	
	private int[] dataA;
	private BufferedImage image;
	
	public Space()
	{
		int width = TetrisConstants.SPACE_WIDTH;
		int height = TetrisConstants.SPACE_HEIGHT_EX;
		int unit   = TetrisConstants.TILE_SIZE;
		
		dataA = new int[ height ];
		Arrays.fill(dataA, TetrisConstants.INIT_SPACE_ROW);
		
		image = new BufferedImage(
				width*unit, 
				height*unit, 
				BufferedImage.TYPE_INT_ARGB);
		
		redraw();
	}
	
	private void redraw() 
	{
		int width = TetrisConstants.SPACE_WIDTH;
		int height = TetrisConstants.SPACE_HEIGHT_EX;
		int offset = TetrisConstants.WALL_WIDTH;
		int unit   = TetrisConstants.TILE_SIZE;
		int ci     = height -1;
		
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		
		// clear
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		// reset composite
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		// draw
		g2.setColor(TetrisConstants.COLOR_TILE);
		
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
	 * �Ƿ���ײ
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
			
			// ����к�Ϊ�������Ѿ������ռ�ײ�
			if (i2 < 0)
				dst = 0xFFFFFFFF;
			// ����к�Խ���˶���������Ϊ�����޵Ŀռ�
			else if (i2 >= TetrisConstants.SPACE_HEIGHT_EX)
				dst = 0x00000000;
			// ��������ͻ�ȡ�ռ����ʵ����
			else
				dst = dataA[i2];
			
			if ((src & dst) != 0)
				return true;
		}
		
		return false;
	}
	
	public void mergeFrom(Shape shape)
	{
		int[] raster = shape.getData();
		int rasterIndex = shape.getY();
		
		for (int y = 0; y < raster.length; y ++)
		{
			int dst_pos = rasterIndex + y;
			
			if (dst_pos < 0)
				; // TODO
			else if (dst_pos < dataA.length)
				dataA[ dst_pos ] |= raster[y];
			else
				; // TODO
		}
		
		redraw();
	}
	
	public void checkGameOver()
	{
		int start = TetrisConstants.SPACE_HEIGHT;
		int end   = TetrisConstants.SPACE_HEIGHT_EX;
		
		for (int i = start; i < end; i ++)
		{
			if (dataA[i] != 0x00000000)
			{
				System.out.println("game over ...");
				return;
			}
		}
	}
	
	/**
	 * ��ȥ��
	 */
	public void clearFullRows()
	{
		for (int i = 0; i < dataA.length; i ++)
		{
			while (isRowFull(dataA[i]))
			{
				deleteRow(i);
			}
		}
		
		redraw();
	}
	
	/**
	 * ��ȥĳһ��
	 * 
	 * ����ߵ��л�����
	 */
	private void deleteRow(int index)
	{
		int topIndex = dataA.length - 1;
		
		while (index < topIndex)
		{
			dataA[ index ] = dataA[ index + 1];
			index ++;
		}
		
		// �²����һ�б���Ҳ�ǳ�ʼ���ݣ������� 0x00000000
		dataA[topIndex] = TetrisConstants.INIT_SPACE_ROW;
	}
	
	private boolean isRowFull(int row)
	{
		return (row & TetrisConstants.MASK_ROW_FULL) == TetrisConstants.MASK_ROW_FULL;
	}
}
