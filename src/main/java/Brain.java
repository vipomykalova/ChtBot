package src.main.java;

import java.util.Arrays;

public class Brain implements Comparable<Brain>{

	private Hangman currentHangman;
	private TruthOrDare currentTruthOrDare;
	public String currentGame;
	private UserRepository userRepository;
	public FSM fsm = new FSM();
	public String username;
	public Integer wins;
	public Integer fails;
	public Long chatId;
	
	public Brain() {
		wins = 0;
		fails = 0;
		fsm.setState(this::startMessage);
	}

	public Brain(UserRepository userRepo, Long id) {
		wins = 0;
		fails = 0;
		userRepository = userRepo;
		chatId = id;
		fsm.setState(this::startMessage);
	}
	
	public int compareTo(Brain other) {
        return wins.compareTo(other.wins);
    }
	
	public BotAnswer startMessage(String input) {
		fsm.setState(this::gameSelection);
		userRepository.getOrCreate(chatId);
		BotAnswer botAnswer = new BotAnswer();
		botAnswer.buttons = Arrays.asList("правда или действие :underage:",
				                          "виселица :detective:",
				                          "о себе :flushed:"); 
		botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		return botAnswer;
	}
	
	public BotAnswer gameSelection(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("виселица")) {
			fsm.setState(this::hangmanWordGeneration);
			currentGame = "виселица";
			botAnswer.buttons = Arrays.asList("ДА:fire:");
			botAnswer.answer = Dialog.INSTANCE.getString("начало");
		}
		else if (input.startsWith("правда или действие")) {
			fsm.setState(this::truthOrDareGetNames);
			currentGame = "правда или действие";
			botAnswer.buttons = Arrays.asList("ДА:fire:");
			botAnswer.answer = Dialog.INSTANCE.getString("начало");
		}
		else if (input.startsWith("о себе")) {
			fsm.setState(this::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
					                          "виселица :detective:",
					                          "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else {
			fsm.setState(this::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
					                          "виселица :detective:",
					                          "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer hangmanWordGeneration(String input) {
		currentHangman = new Hangman(this, userRepository, chatId);
		userRepository.getTopUsers();
		BotAnswer botAnswer = new BotAnswer();
		fsm.setState(currentHangman::hangmanGame);
		botAnswer.buttons = Arrays.asList("о себе :flushed:",
				                          "статистика :heavy_check_mark:",
				                          "стоп :no_entry:");
		botAnswer.answer = currentHangman.setWord();
		return botAnswer;
	}
	
	public BotAnswer truthOrDareGetNames(String input) {
		fsm.setState(this::truthOrDareParseNames);
		BotAnswer botAnswer = new BotAnswer();
		botAnswer.buttons = Arrays.asList();
		botAnswer.answer = Dialog.INSTANCE.getString("игроки");
		return botAnswer;
	}
	
	public BotAnswer truthOrDareParseNames(String input) {
		currentTruthOrDare = new TruthOrDare(this);
		currentTruthOrDare.parseNames(input);
		fsm.setState(currentTruthOrDare::truthOrDareGame);
		BotAnswer botAnswer = new BotAnswer();
		botAnswer.buttons = Arrays.asList("правда :zipper_mouth:",
				                          "действие :tongue:",
				                          "о себе :flushed:",
				                          "стоп :no_entry:"); 
		botAnswer.answer = currentTruthOrDare.askPlayer();
		return botAnswer;
	}
	
	public BotAnswer reply(String input) {
		
		return fsm.update(input);
	}

}
