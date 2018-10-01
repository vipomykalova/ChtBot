import java.util.ArrayList;
import java.util.Scanner;

public class Hangman implements InputOutput{
	
	public boolean win;
	
	public String Input() {
		Scanner in = new Scanner(System.in);
		return in.nextLine().trim();
	}
	
	public void Output(String output) {
		System.out.print(output);
	}
	
	public void Game() {
		String word = MakingTask.newTask();
		char wordToArray[] = word.toCharArray();
		char currentResult[] = new char[word.length()];
		LifeCounter life = new LifeCounter();
		
		for(int i = 0; i < word.length(); i++) {
			currentResult[i] = '-';
		}
		
		PrintResult(word.length(), currentResult);
		win = false;
		boolean finish = false;
		
		while(life.IsHeAlive()) {
			
			String letter = Input();
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
			
			Output("У вас осталось жизней: " + life.lives + "\n");
			
			if(life.lives > 0) {
				PrintResult(word.length(), currentResult);
			}
			
			int count = 0;
			for(int i = 0; i < currentResult.length; i++) {
				if(currentResult[i] != '-') count++;
			}
			if(count == currentResult.length) finish = true;
			
			if(finish) {
				win = true;
				life.lives = 10;
				break;
			}
			
		}
		
		if(!finish) {
			Output("Верное слово: " + word);
		}
	}
	
	private void PrintResult(int n, char[] result) {
		for(int i = 0; i < n; i++) {
			Output("" + result[i]);
		}
		Output("\n");
	}

}
