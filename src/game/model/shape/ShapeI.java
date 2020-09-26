package game.model.shape;

import game.model.Shape;

public class ShapeI extends Shape {
	
	private final static String[] maps = {
		  "0010"
		+ "0010"
		+ "0010"
		+ "0010",
		
		  "0000"
		+ "0000"
		+ "1111"
		+ "0000",
		
	};
	
	@Override
	public int getMapSize() {
		return 4;
	}
	
	@Override
	protected int getMapCount() {
		return maps.length;
	}
	
	@Override
	protected String getShapeData(int shapeIndex)
	{
		return maps[ shapeIndex ];
	}
}
