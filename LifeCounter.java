
public class LifeCounter {
	
	public int lifes = 10;
	
	public int lifeCounter(boolean resultOfStroke) {
		if (!resultOfStroke) lifes -= 1;
		return lifes;
	}
}
