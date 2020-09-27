package game.config;

import java.awt.Color;

public interface TetrisConstants {
	
	int TILE_SIZE = 25;
	/**
	 * 下一个方块预览图的瓦片大小
	 */
	int TILE_SIZE_SMALL = 15;
	
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
	
	// 配色
	Color COLOR_SPACE = new Color(30, 30, 30);
	Color COLOR_EDEN  = new Color(55, 30, 30);
	Color COLOR_TILE   = new Color(245, 245, 245);
	Color COLOR_SHADOW = new Color(255, 255, 255, 30);
	
	int MAX_SHAPE_SIZE = 4;
}
