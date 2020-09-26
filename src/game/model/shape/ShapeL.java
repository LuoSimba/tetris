package game.model.shape;

import game.model.Shape;

public class ShapeL extends Shape {
	
	private final static String[] maps = {
		  "010"
		+ "010"
		+ "011",
		
		  "000"
		+ "111"
		+ "100",
		
		  "110"
		+ "010"
		+ "010",
		
		  "001"
		+ "111"
		+ "000",
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

	@Override
	public char getType() {
		return 'L';
	}
}
