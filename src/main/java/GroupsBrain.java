package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupsBrain {
	
	public ArrayList<String> words;
	private FSM fsm = new FSM();
	private Hangman currentHangman = new Hangman();
	private TaskMaker taskMaker = new TaskMaker();
	private WorkerWithListGroups worker;
	
	public GroupsBrain(WorkerWithListGroups worker) {
		words = new ArrayList<String>();
		fsm.setState(this::hangmanWordGeneration);
		this.worker = worker;
	}
	
	
	public BotAnswer hangmanWordGeneration(String input) {
		fsm.setState(this::hangmanGame);
		currentHangman = new Hangman();
		BotAnswer botAnswer = new BotAnswer();
		if (words.isEmpty()) {
			System.out.println("ss");
			currentHangman.word = taskMaker.newTask("Hangman");
			System.out.println(currentHangman.word);
		}
		else {
			currentHangman.word = worker.getWord(words);
		}
		botAnswer.buttons = Arrays.asList();
		botAnswer.answer = currentHangman.setWord();			
		return botAnswer;
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