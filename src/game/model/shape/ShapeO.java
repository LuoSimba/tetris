package game.model.shape;

import game.model.Shape;

/**
 * Ìï×Ö·½¿é
 */
public class ShapeO extends Shape {
	
	private final static String[] maps = {
		  "0000"
		+ "0110"
		+ "0110"
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
