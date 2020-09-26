package game.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * ���������ɫ
 * 
 * Ӧ��ֻ�� ShapeFactory ��������ʱʹ�á�
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
			// ��Ӱֻʹ�õ����İ�ɫ����Ȼ��������ۻ�����
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
