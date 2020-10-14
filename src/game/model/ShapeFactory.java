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
 * 统一管理方块的创建
 * 
 * 本类内部决定方块的生成顺序
 */
public class ShapeFactory {
	
	/**
	 * 枚举所有的形状
	 */
	private static final String ALL_SHAPES = "ITOFLSZ";
	
	/**
	 * 存放方块实例的队列
	 * 
	 * 从该队列取得方块，并向外部提供
	 * 
	 * 如果队列为空，会自动填充足够的新的方块
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
	 * 生成足够多的方块，放入队列
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
	 * 生成下一个方块
	 * 
	 * 需要给定方块的属性：
	 * 
	 * 1. 形状
	 * 2. 颜色
	 */
	synchronized public Shape genNext()
	{
		if (queue.size() == 0)
			genMoreShapes();
		
		return queue.poll();
	}
}
