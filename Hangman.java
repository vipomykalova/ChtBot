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
	
	public void Game(){
		String word = MakingTask.newTask();
		char wordToArray[] = word.toCharArray();
		char currentResult[] = new char[word.length()];
		LifeCounter life = new LifeCounter();
		
		for(int i = 0; i < word.length(); i++) {
			currentResult[i] = '-';
		}
		
		PrintResult(word.length(), currentResult);
		
		while(true) {
			if(life.lifes == 0) {
				win = false;
				Output("Слово: " + word + "\n");
				life.lifes = 10;
				break;
			}
			
			boolean finish = false;
			String letter = Input();
			
			boolean isItRightLetter = false;
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			for(int i = 0; i < word.length(); i++) {
				if(wordToArray[i] == (letter.charAt(0))) {
					isItRightLetter = true;
					indexes.add(i);
				}
			}
			
			if(isItRightLetter) {
				life.lifes = life.lifeCounter(true);
				for(int i = 0; i < indexes.size(); i++) {
					currentResult[indexes.get(i)] = wordToArray[indexes.get(i)];
					wordToArray[indexes.get(i)] = '-';
				}
			} else life.lifes = life.lifeCounter(false);
			
			Output("У вас осталось жизней: " + life.lifes + "\n");
			
			PrintResult(word.length(), currentResult);
			
			int count = 0;
			for(int i = 0; i < currentResult.length; i++) {
				if(currentResult[i] != '-') count++;
			}
			if(count == currentResult.length) finish = true;
			
			if(finish) {
				win = true;
				life.lifes = 10;
				break;
			}
			
		}
	}
	
	private void PrintResult(int n, char[] result) {
		for(int i = 0; i < n; i++) {
			Output("" + result[i]);
		}
		Output("\n");
	}

}
