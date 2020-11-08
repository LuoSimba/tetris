package game.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * ÅäÉ«·½°¸£º²Êºç
 */
final class ColorSchemeRainbow extends ColorScheme {

	final private Color eden = new Color(55, 30, 30, 220);
	final private Color space = new Color(30, 30, 30);
	final private Color sideBg = new Color(0x333333);
	final private Color tile = new Color(245, 245, 245);
	final private Color shadow = new Color(255, 255, 255, 30);
	
	private List<Color> list;
	private int index;
	final private int SIZE = 6;
	
	public ColorSchemeRainbow()
	{
		list = new ArrayList<Color>();
		index = 0;
		
		for (int i = 0; i < SIZE; i ++)
		{
			float hue = (float)i / SIZE;
			
			Color c = Color.getHSBColor(hue, 0.6F, 1F);
			
			list.add(c);
		}
	}
	
	
	@Override
	public Color getEdenColor() {
		return eden;
	}
	
	@Override
	public Color getShadowColor() {
		return shadow;
	}
	
	@Override
	public Color getSideBackgroundColor() {
		return sideBg;
	}
	
	@Override
	public Color getSpaceColor() {
		return space;
	}
	
	@Override
	public Color getTileColor() {
		return tile;
	}


	@Override
	public void shift() {
		index ++;
		
		if (index >= SIZE)
			index = 0;
	}


	@Override
	public Color getShapeColor() {
		return list.get(index);
	}
}
