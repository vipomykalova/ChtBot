import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hangman {
	
	public boolean win;
	
	public void game(){
		String word = TaskMaker.newTask();
		Map<Character, ArrayList<Integer>> wordsLetters = wordToDict(word);
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
				InOut.INSTANCE.output("Попробуй еще! Я верю в тебя ;) \n");
			} else if(wordsLetters.containsKey(letter.charAt(0))) {
				life.lives = life.lifeCounter(true);

				for(int i = 0; i < wordsLetters.get(letter.charAt(0)).size(); i++) {
					currentResult[wordsLetters.get(letter.charAt(0)).get(i)] = letter.charAt(0);
				}
			} else life.lives = life.lifeCounter(false);

			InOut.INSTANCE.output("У вас осталось жизней: " + life.lives + "\n");

			if(life.lives > 0) {
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

	private Map<Character, ArrayList<Integer>> wordToDict(String word) {
		Map<Character, ArrayList<Integer>> dict = new HashMap<Character, ArrayList<Integer>>();

		for(int i = 0; i < word.length(); i++) {
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			for(int j = i; j < word.length(); j++) {
				if(word.charAt(j) == word.charAt(i))
					indexes.add(j);
			}
			dict.putIfAbsent(word.charAt(i), indexes);
		}
		return dict;
	}
	
	private void printResult(int n, char[] result) {
		for(int i = 0; i < n; i++) {
			InOut.INSTANCE.output("" + result[i]);
		}
		InOut.INSTANCE.output("\n");
	}

}
