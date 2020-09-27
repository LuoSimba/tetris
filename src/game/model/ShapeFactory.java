package game.model;

import game.model.shape.ShapeF;
import game.model.shape.ShapeI;
import game.model.shape.ShapeL;
import game.model.shape.ShapeO;
import game.model.shape.ShapeS;
import game.model.shape.ShapeT;
import game.model.shape.ShapeZ;

import java.awt.Color;

/**
 * ͳһ������Ĵ���
 * 
 * �����ڲ��������������˳��
 */
public class ShapeFactory {

	private String queue = "ITOFLSZ";
	private int index = 0;
	private ColorScheme cs;
	
	public ShapeFactory()
	{
		cs = new ColorScheme();
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
	 * ������һ������
	 * 
	 * ��Ҫ������������ԣ�
	 * 
	 * 1. ��״
	 * 2. ��ɫ
	 */
	public Shape genNext()
	{
		char type = queue.charAt(index);
		Color fg = cs.getColor();
		
		Shape shape = create(type);
		
		shape.shapeIndex = 0;
		
		shape.setPos(3, 20);
		
		shape.setColor(fg);

		// --
		index ++;
		if (index >= queue.length())
			index = 0;
		
		cs.next();
		
		return shape;
	}
}
