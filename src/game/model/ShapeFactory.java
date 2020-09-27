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
 * 统一管理方块的创建
 * 
 * 本类内部决定方块的生成顺序
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
	 * 方块的旋转
	 * 
	 * 实际上是提供一个新的方块。
	 * 
	 * 因为旋转不一定会成功，这样方便回滚
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
	 * 生成下一个方块
	 * 
	 * 需要给定方块的属性：
	 * 
	 * 1. 形状
	 * 2. 颜色
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
