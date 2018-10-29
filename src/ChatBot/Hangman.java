package ChatBot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hangman {

	private Brain currentUser;

	public Hangman(Brain brain) {
		currentUser = brain;
	}

	public enum StatesGame {
		Win, Fail, Game, About, Stop
	}
	public StatesGame currentStateGame;
	public String word = TaskMaker.newTask("Hangman");
	public Map<Character, ArrayList<Integer>> wordsLetters;
	public char resultArray[] = new char[word.length()];
	public LifeCounter life = new LifeCounter();

	public String currentResult(String letter) {
		letter = letter.toLowerCase();
		
		if(letter.startsWith("стоп")) {
			currentStateGame = StatesGame.Stop;
			return Dialog.INSTANCE.getString("прощание");
		}

		if(letter.startsWith("о себе")) {
			currentStateGame = StatesGame.About;
			return Dialog.INSTANCE.getString("расскажи");
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
			return Dialog.INSTANCE.getString("слово") + word + "\n" +
				   Dialog.INSTANCE.getString("победа");
		}

		if(life.lives > 0) {
			currentStateGame = StatesGame.Game;
			return Dialog.INSTANCE.getString("жизни") + life.lives + "\n" +
				   currentWord() + "\n";
		} else {
			currentStateGame = StatesGame.Fail;
			life.lives = 10;
			return Dialog.INSTANCE.getString("проигрыш") +
				   Dialog.INSTANCE.getString("слово") + word + "\n" +
			       Dialog.INSTANCE.getString("еще");
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
		wordsLetters = wordToDict(word);
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

	public String hangmanGame(String input) {
		String result = this.currentResult(input);

		switch(this.currentStateGame) {
			case Win:
				currentUser.fsm.setState(this::wantMore);
				return result;
			case Fail:
				currentUser.fsm.setState(this::wantMore);
				return result;
			case Game:
				currentUser.fsm.setState(this::hangmanGame);
				return result;
			case About:
				currentUser.fsm.setState(this::hangmanGame);
				return result;
			case Stop:
				currentUser.fsm.setState(currentUser::startMessage);
				return result;
		}
		return null;
	}

	public String wantMore(String input) {
		input = input.toLowerCase();
		if (input.startsWith("да")) {
			currentUser.fsm.setState(currentUser::hangmanWordGeneration);
			return Dialog.INSTANCE.getString("начало");
		}
		else if (input.startsWith("нет")) {
			currentUser.fsm.setState(currentUser::startMessage);
			return Dialog.INSTANCE.getString("прощание");
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			return Dialog.INSTANCE.getString("приветствие");
		}
		else {
			currentUser.fsm.setState(this::wantMore);
			return Dialog.INSTANCE.getString("некорректный ввод");
		}
	}
}
