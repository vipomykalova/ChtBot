package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupsBrain {
	
	public ArrayList<String> words;
	private FSM fsm = new FSM();
	private Hangman currentHangman = new Hangman();
	
	public GroupsBrain() {
		words = new ArrayList<String>();
		fsm.setState(this::startMessage);
	}
	
	public BotAnswer startMessage(String input) {
		if (input.startsWith("старт")) {
			fsm.setState(this::hangmanWordGeneration);
			BotAnswer botAnswer = new BotAnswer();
			botAnswer.answer = Dialog.INSTANCE.getString("привет для группы");
			botAnswer.buttons = Arrays.asList();
			return botAnswer;
		}
		else {
			fsm.setState(this::startMessage);
			return null;
		}
	}
	
	public BotAnswer hangmanWordGeneration(String input) {
		currentHangman = new Hangman();
		if (!words.isEmpty() && !input.startsWith("стоп")) {
			fsm.setState(this::hangmanGame);
			BotAnswer botAnswer = new BotAnswer();
			String word = words.get(0);
			words.remove(0);
			currentHangman.word = word;
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = currentHangman.setWord();			
			return botAnswer;
		}
		else if (input.startsWith("стоп")) {
			words.clear();
			fsm.setState(this::startMessage);
			BotAnswer botAnswer = new BotAnswer();
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("пока для группы");
			return botAnswer;
		}
		else {
			fsm.setState(this::hangmanWordGeneration);
			return null;
		}
	}
	
	public BotAnswer hangmanGame(String input) {
		String result = currentHangman.currentResult(input);
		BotAnswer botAnswer = new BotAnswer();
		if (currentHangman.currentStateGame == Hangman.StatesGame.Win) {
			fsm.setState(this::hangmanWordGeneration);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("победа в группе") + result +
					           Dialog.INSTANCE.getString("еще в группе");
		}
		else if (currentHangman.currentStateGame == Hangman.StatesGame.Fail) {
			fsm.setState(this::hangmanWordGeneration);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("проигрыш в группе") + result +
			                   Dialog.INSTANCE.getString("еще в группе");
		}
		else if (currentHangman.currentStateGame == Hangman.StatesGame.Game) {
			fsm.setState(this::hangmanGame);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = result;
		}
		else if (currentHangman.currentStateGame == Hangman.StatesGame.Stop) {
			fsm.setState(this::startMessage);
			words.clear();
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("пока для группы");
		}
		else {
			fsm.setState(this::hangmanGame);
			botAnswer = null;
		}
		return botAnswer;
	}
	
    public BotAnswer reply(String input) {
    	
		return fsm.update(input);
	}
}