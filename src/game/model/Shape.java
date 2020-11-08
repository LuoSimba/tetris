package game.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

import game.config.TetrisConstants;

/**
 * ���飺
 * 1. ���꣺ �����������������ʼ�㣬���������½��������λ��
 * 
 * 2. �����ͼ�λ�ת����λ��Ϣ���Է���ϲ���������
 * 
 * 3. ����������ƶ���ֱ�Ӳ���λ��Ϣ������ı�ͼ�εĶ���
 * 
 * 4. ����������ƶ���ֱ�Ӹ��ķ�������꣬����ı�ͼ�κ�λ��Ϣ
 * 
 * ����ľ���
 * 
 * ����ľ����뷽��ֻ�Ǹ߶ȿ��ܲ�ͬ���� X ���ϵ����������뷽��
 * ����һ�¡�
 * 
 * ��ɫ��
 * ����Ĭ��Ϊ��ɫ������֮������޸�
 * �������ɫ�ǹ̶�����ġ�
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
	 * һ�鷽��ı��ΰ���˳ʱ���˳������
	 * 
	 * ���һ�鷽�鹲�� 4 �ֱ��Σ�����ܵ� index ȡֵΪ [0,1,2,3]
	 */
	public final int getIndex()
	{
		return shapeIndex;
	}
	
	/**
	 * ����
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
	 * �����λ���뱾���ص�
	 */
	public void restoreImage()
	{
		y2 = y;
	}

	/**
	 * ��������
	 */
	public final void imageDown()
	{
		y2 --;
	}
	
	/**
	 * ��������
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
	 * ���Ʒ������ͼƬ�����壬��Ӱ��Ԥ��ͼ
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
