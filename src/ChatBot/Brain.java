package ChatBot;

import java.util.Arrays;

public class Brain {

	public String currentGame;
	public FSM fsm = new FSM();
	private Hangman currentHangman;
	private TruthOrDare currentTruthOrDare;
	private BotAnswer botAnswer = new BotAnswer();

	public Brain() {
		fsm.setState(this::startMessage);
	}
	
	public BotAnswer startMessage(String input) {
		fsm.setState(this::gameSelection);
		botAnswer.buttons = Arrays.asList("правда или действие :underage:",
				                          "виселица :detective:",
				                          "о себе :flushed:"); 
		botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		return botAnswer;
	}
	
	public BotAnswer gameSelection(String input) {
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
		currentHangman = new Hangman(this);
		fsm.setState(currentHangman::hangmanGame);
		botAnswer.buttons = Arrays.asList("о себе :flushed:",
				                          "стоп :no_entry:");
		botAnswer.answer = currentHangman.setWord();
		return botAnswer;
	}
	
	public BotAnswer truthOrDareGetNames(String input) {
		fsm.setState(this::truthOrDareParseNames);
		botAnswer.buttons = Arrays.asList();
		botAnswer.answer = Dialog.INSTANCE.getString("игроки");
		return botAnswer;
	}
	
	public BotAnswer truthOrDareParseNames(String input) {
		currentTruthOrDare = new TruthOrDare(this);
		currentTruthOrDare.parseNames(input);
		fsm.setState(currentTruthOrDare::truthOrDareGame);
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
