package Hangman;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import ChatBot.Dialog;
import ChatBot.TaskMaker;
import ChatBot.Brain;

public class Hangman {
	Brain currentSesion;
	
	public Hangman(Brain brain) {
	    currentSesion = brain;
	}

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
			return word + "\n" + Dialog.INSTANCE.getString("победа") ;
		}

		if(life.lives > 0) {
			currentStateGame = StatesGame.Game;
			return Dialog.INSTANCE.getString("жизни") + + life.lives + "\n" + currentWord() + "\n";
		} else {
			currentStateGame = StatesGame.Fail;
			life.lives = 10;
			return Dialog.INSTANCE.getString("проигрыш") +
					Dialog.INSTANCE.getString("слово") + word + "\n" + Dialog.INSTANCE.getString("еще");
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
	
	public String wantMore(String input) {
		switch(input) {
		case "да":
			currentSesion.fsm.setState(currentSesion::hangmanWordGeneration);
			return Dialog.INSTANCE.getString("начало");	
		case "нет":
			currentSesion.fsm.setState(currentSesion::startMessage);
			return Dialog.INSTANCE.getString("прощание");
		case "о себе":
			currentSesion.fsm.setState(currentSesion::gameSelection);
			return Dialog.INSTANCE.getString("приветствие");		
		}
		currentSesion.fsm.setState(this::wantMore);
		return Dialog.INSTANCE.getString("некорректный ввод");
	}
	
	public String hangmanGame(String input) {    
		String result = this.currentResult(input);
		switch(this.currentStateGame) {
		case Win:
			currentSesion.fsm.setState(this::wantMore);
			return result;
		case Fail:
			currentSesion.fsm.setState(this::wantMore);
			return result;
		case Game:
			currentSesion.fsm.setState(this::hangmanGame);
			return result;
		case Stop:
			currentSesion.fsm.setState(currentSesion::startMessage);
		    return Dialog.INSTANCE.getString("прощание"); 
		}	
		return null;
	}
}
