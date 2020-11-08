package game.model.shape;

import game.model.Brick;

/**
 * Ìï×Ö·½¿é
 */
public class ShapeO extends Brick {
	
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

	@Override
	public char getType() {
		return 'O';
	}
}
