package game.model;

import java.awt.Color;


/**
 * ͬʱ��������뷽�����ɫ����ۣ����������Ӧ����ͳһ�ģ�
 */
public abstract class Skin {
	
	private static Skin defaultSkin;
	
	/**
	 * ��ȡȫ��Ĭ�ϵ�Ƥ��
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
