package main.java;

public class LifeCounter {
	
	public int lives = 10;
	
	public int lifeCounter(boolean resultOfStroke) {
		if (!resultOfStroke) lives -= 1;
		return lives;
	}
	
	public boolean IsHeAlive() {
		return (lives != 0);
	}
}
