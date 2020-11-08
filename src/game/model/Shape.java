package game.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

import game.config.TetrisConstants;

/**
 * 方块：
 * 1. 坐标： 代表方块区域输出的起始点，即方块左下角在盘面的位置
 * 
 * 2. 方块的图形会转化成位信息，以方便合并到盘面上
 * 
 * 3. 方块的左右移动是直接操作位信息，不会改变图形的定义
 * 
 * 4. 方块的上下移动是直接更改方块的坐标，不会改变图形和位信息
 * 
 * 方块的镜像：
 * 
 * 方块的镜像与方块只是高度可能不同，在 X 轴上的坐标总是与方块
 * 保持一致。
 * 
 * 颜色：
 * 方块默认为白色，创建之后可以修改
 * 镜像的颜色是固定不变的。
 */
public abstract class Shape {
	
	private int x;
	private int y;
	private int y2;
	private int[] data;
	protected int shapeIndex;
	private Color fg;
	
	protected Shape()
	{
		int mapSize = getMapSize();
		
		x = 3;
		y = 20;
		y2 = 20;
		shapeIndex = 0;
		data = new int[ mapSize ];
		
		fg = Skin.getDefaultSkin().getTileColor();
		
		parse();
	}

	protected void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.y2 = y;
		
		parse();
		autoFit();
	}
	
	protected void setColor(Color fg)
	{
		this.fg = fg;
	}
	
	protected Color getColor()
	{
		return fg;
	}
	
	public abstract char getType();
	public abstract int getMapSize();
	protected abstract int getMapCount();
	protected abstract String getShapeData(int index);
	
	public boolean isBlock(int x, int y) {
		
		String map = getShapeData(shapeIndex);
		int ci = getMapSize() - 1;
		
		int x1 = x;
		int y1 = -y + ci;
		int index = y1 * getMapSize() + x1;
		
		return map.charAt(index) == '1';
	}
	
	protected int rotatePoints(boolean bClockwise)
	{
		int index = shapeIndex;
		
		if (bClockwise)
			index ++;
		else 
			index --;
		
		if (index == getMapCount())
			index = 0;
		else if (index == -1)
			index = getMapCount() - 1;
		
		return index;
	}
	
	private void parse()
	{
		Arrays.fill(data, 0);
		
		int size = getMapSize();
		
		for (int y = 0; y < size; y ++)
		{
			for (int x = 0; x < size; x ++)
			{
				if (isBlock(x, y))
				{
					data[y] |= 1 << (x + getX() + 4);
				}
			}
		}
	}
	
	private boolean isReachLeft()
	{
		for (int i = 0; i < data.length; i ++)
		{
			if ((data[i] & TetrisConstants.MASK_WALL_LEFT) != 0 )
				return true;
		}
		return false;
	}
	
	private boolean isReachRight()
	{
		for (int i = 0; i < data.length; i ++)
		{
			if ((data[i] & TetrisConstants.MASK_WALL_RIGHT) != 0 )
				return true;
		}
		return false;
	}
	
	private void autoFit()
	{
		while (isReachLeft())
			right();
		
		while (isReachRight())
			left();
	}
	
	public final int getX()
	{
		return x;
	}
	
	public final int getY()
	{
		return y;
	}
	
	public final int getImageY()
	{
		return y2;
	}
	
	/**
	 * 一组方块的变形按照顺时针的顺序排列
	 * 
	 * 如果一组方块共有 4 种变形，则可能的 index 取值为 [0,1,2,3]
	 */
	public final int getIndex()
	{
		return shapeIndex;
	}
	
	/**
	 * 下落
	 */
	public final void down() 
	{
		y --;
	}
	
	public final void up()
	{
		y ++;
	}

	/**
	 * 镜像回位，与本体重叠
	 */
	public void restoreImage()
	{
		y2 = y;
	}

	/**
	 * 镜像下落
	 */
	public final void imageDown()
	{
		y2 --;
	}
	
	/**
	 * 镜像上移
	 */
	public final void imageUp()
	{
		y2 ++;
	}
	
	public void left()
	{
		// check
		for (int i = 0; i < data.length; i ++)
		{
			int row = data[i];
			
			if ((row & TetrisConstants.MASK_BORDER_LEFT) != 0)
				return;
		}
		
		for (int i = 0; i < data.length; i ++)
		{
			data[i] >>= 1;
		}
		
		x --;
	}
	
	public void right()
	{
		// check
		for (int i = 0; i < data.length; i ++)
		{
			int row = data[i];
			
			if ((row & TetrisConstants.MASK_BORDER_RIGHT) != 0)
				return;
		}
		
		for (int i = 0; i < data.length; i ++)
		{
			data[i] <<= 1;
		}
		
		x ++;
	}
	
	public int[] getData()
	{
		return data;
	}
	
	public void paintBody(Graphics2D g)
	{
		paint(g, TetrisConstants.TILE_SIZE, fg);
	}
	
	public void paintImage(Graphics2D g)
	{
		// XXX
		paint(g, TetrisConstants.TILE_SIZE, Skin.getDefaultSkin().getShadowColor());
	}
	
	public void paintPre(Graphics2D g)
	{
		paint(g, TetrisConstants.TILE_SIZE_SMALL, fg);
	}
	
	/**
	 * 绘制方块各种图片：本体，阴影，预览图
	 */
	private void paint(Graphics2D g, final int unit, Color c)
	{
		int size = getMapSize();
		int ci = size -1;
		
		g.setColor(c);
		for (int y = 0; y < size; y ++)
		{
			for (int x = 0; x < size; x ++)
			{
				if (isBlock(x, y))
				{
					g.fillRect(
							x * unit + 1, 
							(ci-y) * unit + 1, 
							unit - 2, 
							unit - 2);
				}

			}
		}
	}
}
