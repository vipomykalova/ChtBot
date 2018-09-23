
public class LifeCounter {
	
	public int lifes = 10;
	
	public int lifeCounter(boolean resultOfStroke) {
		if (resultOfStroke) return lifes;
		else return lifes - 1;
	}

}
