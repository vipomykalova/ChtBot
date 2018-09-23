
public class LifeCounter {
	
	public static int lifes = 10;
	
	public static int lifeCounter(boolean resultOfStroke) {
		if (!resultOfStroke) lifes -= 1;
		return lifes;
	}
}
