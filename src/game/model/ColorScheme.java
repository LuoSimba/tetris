package game.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理方块的配色
 * 
 * 应当只在 ShapeFactory 创建方块时使用。
 */
public class ColorScheme {

	
	private List<Color> list;
	private List<Color> list2;
	private int index;
	private int size;
	
	public ColorScheme()
	{
		list  = new ArrayList<Color>();
		list2 = new ArrayList<Color>();
	
		index = 0;
		size = 6;
		
		for (int i = 0; i < size; i ++)
		{
			float hue = (float)i / size;

			Color c  = Color.getHSBColor(hue, 0.6F, 1F);
			// 阴影只使用淡淡的白色，不然界面会变得眼花缭乱
			Color c2 = new Color(255, 255, 255, 30);
			
			list.add(c);
			list2.add(c2);
		}
	}
	
	public void next()
	{
		index ++;
		
		if (index >= size)
			index = 0;
	}
	
	public Color getColor()
	{
		return list.get(index);
	}
	
	public Color getImgColor()
	{
		return list2.get(index);
	}
}
