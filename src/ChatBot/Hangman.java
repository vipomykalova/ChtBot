package ChatBot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hangman {

	private Brain currentUser;
	private TaskMaker taskMaker = new TaskMaker();
	public LifeCounter life;

	public Hangman(Brain brain) {
		currentUser = brain;
		life = new LifeCounter();
	}

	public enum StatesGame {
		Win, Fail, Game, About, Stop
	}
	
	public StatesGame currentStateGame;
	public String word = taskMaker.newTask("Hangman");
	private Map<Character, ArrayList<Integer>> wordsLetters;
	private char resultArray[] = new char[word.length()];

	public String currentResult(String letter) {	
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

	public Map<String, List<String>> hangmanGame(String input) {
		String result = this.currentResult(input);
		Map<String, List<String>> curMap = new HashMap<>();
		List<String> curListButtons;
		if (this.currentStateGame == Hangman.StatesGame.Win) {
			currentUser.fsm.setState(this::wantMore);
			curListButtons = Arrays.asList("ДА:fire:",
					                       "НЕТ:hankey:",
					                       "о себе :flushed:"); 
            curMap.put(result, curListButtons);
		}
		else if (this.currentStateGame == Hangman.StatesGame.Fail) {
			currentUser.fsm.setState(this::wantMore);
			curListButtons = Arrays.asList("ДА:fire:",
					                       "НЕТ:hankey:",
					                       "о себе :flushed:"); 
            curMap.put(result, curListButtons);
		}
		else if (this.currentStateGame == Hangman.StatesGame.Game) {
			currentUser.fsm.setState(this::hangmanGame);
			curListButtons = Arrays.asList("о себе :flushed:",
                                           "стоп :no_entry:"); 
            curMap.put(result, curListButtons);
		}
		else if (this.currentStateGame == Hangman.StatesGame.About) {
			currentUser.fsm.setState(this::hangmanGame);
			curListButtons = Arrays.asList("о себе :flushed:",
                                           "стоп :no_entry:"); 
            curMap.put(result, curListButtons);
		}
		else {
			currentUser.fsm.setState(currentUser::startMessage);
			curListButtons = Arrays.asList(":hand:"); 
            curMap.put(result, curListButtons);
		}
		return curMap;
	}

	public Map<String, List<String>> wantMore(String input) {
		Map<String, List<String>> curMap = new HashMap<>();
		List<String> curListButtons;
		if (input.startsWith("да")) {
			currentUser.fsm.setState(currentUser::hangmanWordGeneration);
			curListButtons = Arrays.asList("ДА:fire:");
			curMap.put(Dialog.INSTANCE.getString("начало"), curListButtons);
		}
		else if (input.startsWith("нет")) {
			currentUser.fsm.setState(currentUser::startMessage);
			curListButtons = Arrays.asList(":hand:");
			curMap.put(Dialog.INSTANCE.getString("прощание"), curListButtons);
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::wantMore);
			curListButtons = Arrays.asList("ДА:fire:",
                                           "НЕТ:hankey:",
                                           "о себе :flushed:"); 
            curMap.put(Dialog.INSTANCE.getString("расскажи"), curListButtons);
		}
		else {
			currentUser.fsm.setState(this::wantMore);
			curListButtons = Arrays.asList("ДА:fire:",
                                           "НЕТ:hankey:",
                                           "о себе :flushed:"); 
            curMap.put(Dialog.INSTANCE.getString("некорректный ввод"), curListButtons);
		}
		return curMap;
	}
}
