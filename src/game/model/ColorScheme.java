package game.model;

import java.awt.Color;


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
			defaultCs = new ColorSchemeClassical();
		
		return defaultCs;
	}
	
	
	protected ColorScheme()
	{
	}
	
	
	abstract public void shift();
	abstract public Color getShapeColor();
	
	abstract public Color getShadowColor();
	abstract public Color getSpaceColor();
	abstract public Color getEdenColor();
	abstract public Color getSideBackgroundColor();
	abstract public Color getTileColor();
	
	abstract public Color getTextColor();
}
