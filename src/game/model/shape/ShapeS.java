package game.model.shape;

import game.model.Brick;

public class ShapeS extends Brick {
	
	
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

	@Override
	public char getType() {
		return 'S';
	}
}
