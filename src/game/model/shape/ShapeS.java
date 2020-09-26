package game.model.shape;

import game.model.Shape;

public class ShapeS extends Shape {
	
	
	private final static String[] maps = {
		  "011"
		+ "110"
		+ "000",
		
		  "100"
		+ "110"
		+ "010",
	};
	
	@Override
	public int getMapSize() {
		return 3;
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
