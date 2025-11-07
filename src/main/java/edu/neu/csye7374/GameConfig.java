package edu.neu.csye7374;

public class GameConfig {

	private static final GameConfig INSTANCE = new GameConfig();
	private int difficulty = 1;

	private GameConfig() {}

	public static GameConfig getInstance() {
		return INSTANCE;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
