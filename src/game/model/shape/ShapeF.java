package game.model.shape;

import game.model.Shape;

public class ShapeF extends Shape {

	private final static String[] maps = {
		  "011"
		+ "010"
		+ "010",
		
		  "000"
		+ "111"
		+ "001",
		
		  "010"
		+ "010"
		+ "110",
		
		  "100"
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
		return 'F';
	}
}
