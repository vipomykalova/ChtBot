import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
	
	public static boolean win;
	
	public static void Game() {
		Scanner in = new Scanner(System.in);
		String word = MakingTask.newTask();
		char wordToArray[] = word.toCharArray();
		char currentResult[] = new char[word.length()];
		int lifes = LifeCounter.lifes;
		
		for(int i = 0; i < word.length(); i++) {
			currentResult[i] = '-';
		}
		
		PrintResult(word.length(), currentResult);
		
		while(true) {
			if(lifes == 0) {
				win = false;
				break;
			}
			
			boolean finish = false;
			String letter = in.nextLine();
			
			boolean isItRightLetter = false;
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			for(int i = 0; i < word.length(); i++) {
				if(wordToArray[i] == (letter.charAt(0))) {
					isItRightLetter = true;
					indexes.add(i);
				}
			}
			
			if(isItRightLetter) {
				lifes = LifeCounter.lifeCounter(true);
				for(int i = 0; i < indexes.size(); i++) {
					currentResult[indexes.get(i)] = wordToArray[indexes.get(i)];
					wordToArray[indexes.get(i)] = '-';
				}
			} else lifes = LifeCounter.lifeCounter(false);
			
			PrintResult(word.length(), currentResult);
			
			int count = 0;
			for(int i = 0; i < currentResult.length; i++) {
				if(currentResult[i] != '-') count++;
			}
			if(count == currentResult.length) finish = true;
			
			if(finish) {
				win = true;
				break;
			}
			
		}
	}
	
	private static void PrintResult(int n, char[] result) {
		for(int i = 0; i < n; i++) {
			System.out.print(result[i]);
		}
		System.out.print("\n");
	}

}
