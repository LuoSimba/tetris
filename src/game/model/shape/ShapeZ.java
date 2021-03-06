package game.model.shape;

import game.model.Brick;

public class ShapeZ extends Brick {
	
	
	private final static String[] maps = {
		  "110"
		+ "011"
		+ "000",
		
		  "001"
		+ "011"
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
		return 'Z';
	}
}
