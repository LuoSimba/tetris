package game.model;

import java.awt.Color;

/**
 * 配色方案：经典
 */
public class ColorSchemeClassical extends ColorScheme {
	
	private final Color black = new Color(0x060307);
	private final Color blackShadow = new Color(0x06, 0x03, 0x07, 0x19);
	private final Color white = new Color(0x9ead86);

	@Override
	public Color getEdenColor() {
		return blackShadow;
	}

	@Override
	public Color getShadowColor() {
		return blackShadow;
	}

	@Override
	public Color getShapeColor() {
		return black;
	}

	@Override
	public Color getSideBackgroundColor() {
		return white;
	}

	@Override
	public Color getSpaceColor() {
		return white;
	}

	@Override
	public Color getTileColor() {
		return black;
	}

	@Override
	public void shift() {
		// do nothing
	}

	@Override
	public Color getTextColor() {
		return black;
	}
}
