package Hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hangman {

	public enum StatesGame {
		Win, Fail, Game, Stop
	}
	public StatesGame currentStateGame;
	public String word = TaskMaker.newTask("Hangman");
	public Map<Character, ArrayList<Integer>> wordsLetters = wordToDict(word);
	public char resultArray[] = new char[word.length()];
	public LifeCounter life = new LifeCounter();

	public String currentResult(String letter) {
		if(letter.equals("стоп")) {
			currentStateGame = StatesGame.Stop;
			return null;
		}
		
		if(wordsLetters.containsKey(letter.charAt(0))) {
			life.lives = life.lifeCounter(true);

			for(int i = 0; i < wordsLetters.get(letter.charAt(0)).size(); i++) {
				resultArray[wordsLetters.get(letter.charAt(0)).get(i)] = letter.charAt(0);
			}
		} else life.lives = life.lifeCounter(false);

		int count = 0;
		for(int i = 0; i < resultArray.length; i++) {
			if(resultArray[i] != '-') count++;

		}
		if(count == resultArray.length) {
			currentStateGame = StatesGame.Win;
			life.lives = 10;
			return word + "\n" + "Молодец! Сыграем еще?" + "\n";
		}

		if(life.lives > 0) {
			currentStateGame = StatesGame.Game;
			return "У вас осталось жизней " + life.lives + "\n" + currentWord() + "\n";
		} else {
			currentStateGame = StatesGame.Fail;
			life.lives = 10;
			return "Ты проиграл :( \n" + "Загаданное слово: " + word + "\n" + "Сыграем еще?" + "\n";
		}
	}

	public Map<Character, ArrayList<Integer>> wordToDict(String word) {
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

	public String setWord() {
		for(int i = 0; i < word.length(); i++) {
			resultArray[i] = '-';
		}
		return currentWord() + "\n";
	}

	public String currentWord() {
		String result = "";

		for(int i = 0; i < word.length(); i++){
			result += resultArray[i];
		}
		return result;
	}
}
