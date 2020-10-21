package game.model;

public enum Command {

	LEFT,
	RIGHT,
	DOWN,
	ROTATE,
	
	PAUSE,
	RESUME,
	
	NEXT_SHAPE,  // debug
	
	/**
	 * 由外部发送给游戏实例, 指示游戏必须无条件
	 * 终止运行
	 * 
	 * (不受游戏规则制约, 强制终止运行)
	 */
	KILL,
}
