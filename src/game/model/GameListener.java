package game.model;

public interface GameListener {
	
	void onGameStart();

	void onGamePause();
	
	void onGameResume();
	
	void onGameOver();
	
	void onDispose();
}
