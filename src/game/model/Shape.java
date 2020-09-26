package game.model;

import java.awt.Graphics2D;
import java.util.Arrays;

import game.config.TetrisConstants;
import game.model.shape.ShapeF;
import game.model.shape.ShapeI;
import game.model.shape.ShapeL;
import game.model.shape.ShapeO;
import game.model.shape.ShapeS;
import game.model.shape.ShapeT;
import game.model.shape.ShapeZ;

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
 */
public abstract class Shape {
	
	public static Shape create(char type) {
		return create(type, 0, 3, 20);
	}
	
	private static Shape create(char type, int index, int x, int y)
	{
		Shape shape = null;
		
		switch (type)
		{
		case 'L':
			shape = new ShapeL();
			break;
		case 'T':
			shape = new ShapeT();
			break;
		case 'Z':
			shape = new ShapeZ();
			break;
		case 'I':
			shape = new ShapeI();
			break;
		case 'O':
			shape = new ShapeO();
			break;
		case 'F':
			shape = new ShapeF();
			break;
		case 'S':
			shape = new ShapeS();
			break;
		default:
			shape = new ShapeT();
		}
		
		shape.type = type;
		shape.shapeIndex = index;
		shape.x = x;
		shape.y = y;
		shape.y2 = y;
		shape.parse();
		shape.autoFit();
		
		return shape;
	}
	
	private char type;
	private int x;
	private int y;
	private int y2;
	private int[] data;
	protected int shapeIndex;
	
	protected Shape()
	{
		int mapSize = getMapSize();
		
		x = 3;
		y = 20;
		y2 = 20;
		shapeIndex = 0;
		data = new int[ mapSize ];
		parse();
	}

	
	
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
	
	private int rotatePoints(boolean bClockwise)
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
	
	/**
	 * 方块的旋转
	 * 
	 * 实际上是提供一个新的方块。
	 * 
	 * 因为旋转不一定会成功，这样方便回滚
	 */
	public Shape rotate(boolean bClockwise)
	{
		char type = this.type;
		int index = rotatePoints(bClockwise);
		return create(type, index, x, y);
		
		//autoFit();
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
	
	/**
	 * 绘制方块
	 * 
	 * 外部已经设置好了方块的颜色，这里只需要在
	 * 合适的位置绘图。
	 */
	public void paint(Graphics2D g, final int unit)
	{
		int size = getMapSize();
		int ci = size -1;
		
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
