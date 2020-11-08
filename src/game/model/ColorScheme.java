package game.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理方块的配色
 * 
 * 应当只在 ShapeFactory 创建方块时使用。
 * 
 * 同时管理界面的配色（方块与界面应该是统一的）
 */
public abstract class ColorScheme {
	
	private static ColorScheme defaultCs;
	
	public static ColorScheme getDefaultColorScheme()
	{
		if (defaultCs == null)
			defaultCs = new ColorSchemeRainbow();
		
		return defaultCs;
	}

	
	private List<Color> list;
	private int index;
	private int size;
	
	protected ColorScheme()
	{
		list  = new ArrayList<Color>();
	
		index = 0;
		size = 6;
		
		for (int i = 0; i < size; i ++)
		{
			float hue = (float)i / size;

			Color c  = Color.getHSBColor(hue, 0.6F, 1F);
			
			list.add(c);
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
		// XXX
		return new Color(255, 255, 255, 30);
	}
	
	public Color getSpaceColor()
	{
		// XXX
		return new Color(30, 30, 30);
	}
	
	public Color getEdenColor()
	{
		// XXX
		return new Color(55, 30, 30, 220);
	}
	
	public Color getSideBackgroundColor()
	{
		// XXX
		return new Color(0x333333);
	}
	
	public Color getTileColor()
	{
		// XXX
		return new Color(245, 245, 245);
	}
}
