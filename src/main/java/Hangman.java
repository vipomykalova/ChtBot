package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Hangman {

	private Brain currentUser;
	private Boolean isGroup = false;
	private MakerOfStatistics makerOfStatistics;
	private UserRepository userRepository;
	private Long chatId;
	private TaskMaker taskMaker = new TaskMaker();
	public LifeCounter life;
	
	public Hangman() {
		isGroup = true;
		life = new LifeCounter();
	}

	public Hangman(Brain brain, UserRepository userRepo, Long id) {
		currentUser = brain;
		userRepository = userRepo;
		chatId = id;
		makerOfStatistics = new MakerOfStatistics();
		life = new LifeCounter();
	}

	public enum StatesGame {
		Win, Fail, Game, About, Stop, Statistics
	}
	
	public StatesGame currentStateGame;
	public String word = taskMaker.newTask("Hangman");
	private Map<Character, ArrayList<Integer>> wordsLetters;
	private char resultArray[];

	public String currentResult(String letter) {

		if(letter.startsWith("стоп")) {
			currentStateGame = StatesGame.Stop;
			return Dialog.INSTANCE.getString("прощание");
		}
		
		if(letter.startsWith("статистика")) {
			currentStateGame = StatesGame.Statistics;
			return makerOfStatistics.getStatistics(userRepository.getTopUsers());
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
			if (!isGroup) {
				User user = userRepository.getOrCreate(chatId);
				user.wins += 1;			
				userRepository.saveInDatabase(chatId, user);
			}
			return Dialog.INSTANCE.getString("слово") + word + "\n";
		}

		if(life.lives > 0) {
			currentStateGame = StatesGame.Game;
			return Dialog.INSTANCE.getString("жизни") + life.lives + "\n" +
				   currentWord() + "\n";
		} else {
			currentStateGame = StatesGame.Fail;
			life.lives = 10;
			if (!isGroup) {
				User user = userRepository.getOrCreate(chatId);
				user.fails += 1;				
				userRepository.saveInDatabase(chatId, user);
			}
			return Dialog.INSTANCE.getString("слово") + word + "\n";
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
		resultArray = new char[word.length()];
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

	public BotAnswer hangmanGame(String input) {
		String result = this.currentResult(input);
		BotAnswer botAnswer = new BotAnswer();
		if (this.currentStateGame == Hangman.StatesGame.Win) {
			currentUser.fsm.setState(this::wantMore);
			botAnswer.buttons = Arrays.asList("ДА:fire:",
					                          "НЕТ:hankey:",
					                          "статистика :heavy_check_mark:",
					                          "о себе :flushed:"); 
			botAnswer.answer = result + Dialog.INSTANCE.getString("победа");
		}
		else if (this.currentStateGame == Hangman.StatesGame.Fail) {
			currentUser.fsm.setState(this::wantMore);
			botAnswer.buttons = Arrays.asList("ДА:fire:",
					                          "НЕТ:hankey:",
					                          "статистика :heavy_check_mark:",
					                          "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("проигрыш") + result +
					           Dialog.INSTANCE.getString("еще");
		}
		else if (this.currentStateGame == Hangman.StatesGame.Statistics) {
			currentUser.fsm.setState(this::hangmanGame);
			botAnswer.buttons = Arrays.asList("о себе :flushed:",
					                          "статистика :heavy_check_mark:",
                                              "стоп :no_entry:"); 
			botAnswer.answer = result;
		}
		else if (this.currentStateGame == Hangman.StatesGame.Game) {
			currentUser.fsm.setState(this::hangmanGame);
			botAnswer.buttons = Arrays.asList("о себе :flushed:",
					                          "статистика :heavy_check_mark:",
                                              "стоп :no_entry:"); 
			botAnswer.answer = result;
		}
		else if (this.currentStateGame == Hangman.StatesGame.About) {
			currentUser.fsm.setState(this::hangmanGame);
			botAnswer.buttons = Arrays.asList("о себе :flushed:",
					                          "статистика :heavy_check_mark:",
                                              "стоп :no_entry:"); 
			botAnswer.answer = result;
		}
		else {
			currentUser.fsm.setState(currentUser::startMessage);
			botAnswer.buttons = Arrays.asList(":hand:"); 
			botAnswer.answer = result;
		}
		return botAnswer;
	}
	
	public BotAnswer wantMore(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("да")) {
			currentUser.fsm.setState(currentUser::hangmanWordGeneration);
			botAnswer.buttons = Arrays.asList("ДА:fire:");
			botAnswer.answer = Dialog.INSTANCE.getString("начало");
		}
		else if (input.startsWith("нет")) {
			currentUser.fsm.setState(currentUser::startMessage);
			botAnswer.buttons = Arrays.asList(":hand:");
			botAnswer.answer = Dialog.INSTANCE.getString("прощание");
		}
		else if (input.startsWith("статистика")) {
			currentUser.fsm.setState(this::wantMore);
			botAnswer.buttons = Arrays.asList("ДА:fire:",
                                              "НЕТ:hankey:",
                                              "статистика :heavy_check_mark:",
                                              "о себе :flushed:");
			botAnswer.answer = makerOfStatistics.getStatistics(userRepository.getTopUsers());
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::wantMore);
			botAnswer.buttons = Arrays.asList("ДА:fire:",
                                              "НЕТ:hankey:",
                                              "статистика :heavy_check_mark:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::wantMore);
			botAnswer.buttons = Arrays.asList("ДА:fire:",
                                              "НЕТ:hankey:",
                                              "статистика :heavy_check_mark:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
}