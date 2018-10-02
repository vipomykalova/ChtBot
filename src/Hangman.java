import java.util.ArrayList;

public class Hangman {
	
	public boolean win;
	
	public void game(){
		String word = TaskMaker.newTask();
		char wordToArray[] = word.toCharArray();
		char currentResult[] = new char[word.length()];
		LifeCounter life = new LifeCounter();
		
		for(int i = 0; i < word.length(); i++) {
			currentResult[i] = '-';
		}
		
		printResult(word.length(), currentResult);
		win = false;
		boolean finish = false;
		
		while(life.IsHeAlive() || finish) {
			String letter = InOut.INSTANCE.input();
			if(letter.isEmpty()) {
				continue;
			}
			
			boolean isItRightLetter = false;
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			
			for(int i = 0; i < word.length(); i++) {
 				if(wordToArray[i] == (letter.charAt(0))) {
 					isItRightLetter = true;
 					indexes.add(i);
 				}
			}
			
			if(isItRightLetter) {
				life.lives = life.lifeCounter(true);
				for(int i = 0; i < indexes.size(); i++) {
					currentResult[indexes.get(i)] = wordToArray[indexes.get(i)];
					wordToArray[indexes.get(i)] = '-';
				}
			} else life.lives = life.lifeCounter(false);
			
			InOut.INSTANCE.output("У вас осталось жизней: " + life.lives + "\n");
			
			if (life.lives > 0) {
				printResult(word.length(), currentResult);
			}
			
			int count = 0;
			for(int i = 0; i < currentResult.length; i++) {
 				if(currentResult[i] != '-') count++;
 			}
 			if(count == currentResult.length) finish = true;
		}
		if (!finish) {
			InOut.INSTANCE.output("Верное слово: "+ word + "\n");
		} else {
			win = true;
			life.lives = 10;
		}
	}
	
	private void printResult(int n, char[] result) {
		for(int i = 0; i < n; i++) {
			InOut.INSTANCE.output("" + result[i]);
		}
		InOut.INSTANCE.output("\n");
	}

}
