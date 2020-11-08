package game.model;

import java.awt.Color;


/**
 * ���������ɫ
 * 
 * Ӧ��ֻ�� ShapeFactory ��������ʱʹ�á�
 * 
 * ͬʱ����������ɫ�����������Ӧ����ͳһ�ģ�
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
