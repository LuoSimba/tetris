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
	private LinkedList<Shape> queue;
	
	private ColorScheme cs;
	
	public ShapeFactory()
	{
		cs = new ColorScheme();
		
		queue = new LinkedList<Shape>();
	}
	
	private Shape create(char type)
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
	public Shape rotate(Shape old)
	{
		char type = old.getType();
		Color fg = old.getColor();
		int index = old.rotatePoints(true);
		int x = old.getX();
		int y = old.getY();
		
		Shape shape = create(type);
		shape.shapeIndex = index;
		shape.setPos(x, y);
		shape.setColor(fg);
		
		return shape;
	}
	
	/**
	 * �����㹻��ķ��飬�������
	 */
	private void genMoreShapes()
	{
		for (int i = 0; i < ALL_SHAPES.length(); i ++)
		{
			char type = ALL_SHAPES.charAt(i);
			
			Color fg = cs.getColor();
			
			Shape shape = create(type);
			
			shape.shapeIndex = 0;
			
			shape.setPos(3, 20);
			
			shape.setColor(fg);
			
			queue.add(shape);
			cs.next();
		}
	}
	
	/**
	 * ������һ������
	 * 
	 * ��Ҫ������������ԣ�
	 * 
	 * 1. ��״
	 * 2. ��ɫ
	 */
	synchronized public Shape genNext()
	{
		if (queue.size() == 0)
			genMoreShapes();
		
		return queue.poll();
	}
}
