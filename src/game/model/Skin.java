package game.model;

import java.awt.Color;


/**
 * 同时管理界面与方块的配色，外观（方块与界面应该是统一的）
 */
public abstract class Skin {
	
	private static Skin defaultSkin;
	
	/**
	 * 获取全局默认的皮肤
	 */
	public static Skin getDefaultSkin()
	{
		if (defaultSkin == null) {
			defaultSkin = new ColorSchemeClassical();
			//defaultSkin = new ColorSchemeRainbow();
		}
		
		return defaultSkin;
	}
	
	
	protected Skin()
	{
	}
	
	
	abstract public void shiftColor();
	abstract public Color getShapeColor();
	
	abstract public Color getShadowColor();
	abstract public Color getSpaceColor();
	abstract public Color getEdenColor();
	abstract public Color getSideBackgroundColor();
	abstract public Color getTileColor();
	
	abstract public Color getTextColor();
	abstract public Color getBorderColor();
}
