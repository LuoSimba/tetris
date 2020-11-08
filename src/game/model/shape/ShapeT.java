package game.model.shape;

import game.model.Brick;

public class ShapeT extends Brick {
	
	private final static String[] maps = {
		  "010"
		+ "111"
		+ "000",
		
		  "010"
		+ "011"
		+ "010",
		
		  "000"
		+ "111"
		+ "010",
		
		  "010"
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
		return 'T';
	}
}
