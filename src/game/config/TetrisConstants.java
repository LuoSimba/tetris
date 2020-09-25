package game.config;

import java.awt.Color;

public interface TetrisConstants {
	
	int TILE_SIZE = 25;
	
	int SPACE_WIDTH = 10;
	int SPACE_HEIGHT = 20;
	int SPACE_HEIGHT_EX = SPACE_HEIGHT + 5;
	int WALL_WIDTH = 4;

	int MASK_WALL_LEFT = (1 << WALL_WIDTH) -1;
	int MASK_WALL_RIGHT = MASK_WALL_LEFT << (WALL_WIDTH + SPACE_WIDTH);
	
	int MASK_BORDER_LEFT = (1 << 5) -1;
	int MASK_BORDER_RIGHT = MASK_BORDER_LEFT << (WALL_WIDTH + 9);
	
	int MASK_ROW_FULL  = ((1 << SPACE_WIDTH) -1) << WALL_WIDTH;

	int INIT_SPACE_ROW = ~ MASK_ROW_FULL;
	
	// ÅäÉ«
	Color COLOR_TILE  = new Color(245, 245, 245, 255);
	Color COLOR_IMAGE = new Color(255, 0, 0, 50);
	Color COLOR_SPACE = new Color(30, 30, 30);
	Color COLOR_EDEN  = new Color(55, 30, 30);
}
