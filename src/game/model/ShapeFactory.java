package game.model;

import game.model.shape.ShapeF;
import game.model.shape.ShapeI;
import game.model.shape.ShapeL;
import game.model.shape.ShapeO;
import game.model.shape.ShapeS;
import game.model.shape.ShapeT;
import game.model.shape.ShapeZ;

import java.awt.Color;
import java.util.LinkedList;

/**
 * ͳһ������Ĵ���
 * 
 * �����ڲ��������������˳��
 */
public class ShapeFactory {
	
	/**
	 * ö�����е���״
	 */
	private static final String ALL_SHAPES = "ITOFLSZ";
	
	/**
	 * ��ŷ���ʵ���Ķ���
	 * 
	 * �Ӹö���ȡ�÷��飬�����ⲿ�ṩ
	 * 
	 * �������Ϊ�գ����Զ�����㹻���µķ���
	 */
	private LinkedList<Brick> queue;
	
	private Skin skin;
	
	public ShapeFactory()
	{
		skin = Skin.getDefaultSkin();
		
		queue = new LinkedList<Brick>();
	}
	
	private Brick create(char type)
	{
		switch (type)
		{
		case 'L':
			return new ShapeL();
		case 'T':
			return new ShapeT();
		case 'Z':
			return new ShapeZ();
		case 'I':
			return new ShapeI();
		case 'O':
			return new ShapeO();
		case 'F':
			return new ShapeF();
		case 'S':
			return new ShapeS();
		default:
			System.out.println("Error: unknown shape type " + type);
		}
		
		return null;
	}
	
	/**
	 * �������ת
	 * 
	 * ʵ�������ṩһ���µķ��顣
	 * 
	 * ��Ϊ��ת��һ����ɹ�����������ع�
	 */
	public Brick rotate(Brick old)
	{
		char type = old.getType();
		Color fg = old.getColor();
		int index = old.rotatePoints(true);
		int x = old.getX();
		int y = old.getY();
		
		Brick shape = create(type);
		shape.shapeIndex = index;
		shape.setPos(x, y);
		shape.setColor(fg);
		
		return shape;
	}
	
	/**
	 * ����һ�鷽�飬�������
	 * 
	 * ����ģ���ַ���ָ����˳��һ��һ�����ɷ���
	 * 
	 * ��Ҫ������������ԣ�
	 * 
	 * 1. ��״
	 * 2. ��ɫ
	 */
	private void genMoreShapes(String templete)
	{
		for (int i = 0; i < templete.length(); i ++)
		{
			char type = templete.charAt(i);
			
			Color fg = skin.getShapeColor();
			
			Brick shape = create(type);
			
			shape.shapeIndex = 0;
			
			shape.setPos(3, 20);
			
			shape.setColor(fg);
			
			queue.add(shape);
			
			skin.shiftColor();
		}
	}
	
	private void genBag7()
	{
		StringBuffer temp = new StringBuffer(ALL_SHAPES);
		
		// ����˳��
		for (int i = temp.length() -1; i > 0; i --)
		{
			int tar = Util.random(i + 1);
			
			if (tar != i)
			{
				char ci = temp.charAt(i);
				temp.setCharAt(i, temp.charAt(tar));
				temp.setCharAt(tar, ci);
			}
		}
		
		genMoreShapes(temp.toString());
	}
	
	/**
	 * ��ȡ��һ������
	 */
	synchronized public Brick genNext()
	{
		if (queue.size() == 0)
		{
			// ����һ��̶���˳�����ɷ���
			//genMoreShapes(ALL_SHAPES);
			// ���� BAG7 ��ʽ���ɷ���
			genBag7();
		}
		
		return queue.poll();
	}
}
