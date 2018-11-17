package main.java;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TruthOrDare {

	public enum StatesGame {
		Correct, Incorrect, About, Stop
	}

	private Brain currentUser;
	private TaskMaker taskMaker;
	private Random rnd = new Random();
	public StatesGame currentStateGame;
	public HashMap<String, String> nameArchive;
	public String[] gamers;
	
	public TruthOrDare(Brain brain) {
		currentUser = brain;
		taskMaker = new TaskMaker();
		nameArchive = new HashMap<>();
		nameArchive.put("правда", "Truth");
		nameArchive.put("действие", "Dare");
	}

	public String taskForPlayer(String answer){
		checkState(answer);
		switch(currentStateGame) {
		case Correct:
			if (answer.contains("правда")) {
				return taskMaker.newTask(nameArchive.get("правда")) + "\n";
			} else {
				return taskMaker.newTask(nameArchive.get("действие")) + "\n";
			}
		case Incorrect:
			return Dialog.INSTANCE.getString("некорректный ввод");
		case About:
			return Dialog.INSTANCE.getString("расскажи");
		case Stop:
			return Dialog.INSTANCE.getString("прощание");
		}
		return null;
	}
	
	public void checkState(String answer) {
		if (answer.startsWith("стоп")) {
			currentStateGame = StatesGame.Stop;
		} else if (answer.startsWith("правда") || answer.startsWith("действие")) {
			currentStateGame = StatesGame.Correct;
		} else if (answer.startsWith("о себе")) {
			currentStateGame = StatesGame.About;
		} else {
			currentStateGame = StatesGame.Incorrect;
		}
	}

	public String askPlayer() {
		String currentGamer = gamers[rnd.nextInt(gamers.length)];
		return currentGamer + Dialog.INSTANCE.getString("что из");
	}

	public void parseNames(String names) {
		gamers = names.split(",");
	}

	public BotAnswer truthOrDareAskPlayer(String input) {
		this.checkState(input);
		BotAnswer botAnswer = new BotAnswer();
		if (this.currentStateGame == TruthOrDare.StatesGame.Stop) {
			currentUser.fsm.setState(currentUser::startMessage);
			botAnswer.buttons = Arrays.asList(":hand:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("прощание");
			return botAnswer;
		}
		currentUser.fsm.setState(this::truthOrDareGame);
		botAnswer.buttons = Arrays.asList("правда :zipper_mouth:",
				                          "действие :tongue:",
				                          "о себе :flushed:",
				                          "стоп :no_entry:");
		botAnswer.answer = this.askPlayer();
		return botAnswer;
	}

	public BotAnswer truthOrDareGame(String input) {
		String result = this.taskForPlayer(input);
		BotAnswer botAnswer = new BotAnswer();
		if (this.currentStateGame == TruthOrDare.StatesGame.Correct) {
			currentUser.fsm.setState(this::truthOrDareAskPlayer);	
			botAnswer.buttons = Arrays.asList("OK :v:"); 
			botAnswer.answer = result;
		}
		else if (this.currentStateGame == TruthOrDare.StatesGame.Incorrect) {
			currentUser.fsm.setState(this::truthOrDareGame);
			botAnswer.buttons = Arrays.asList("правда :zipper_mouth:",
                                              "действие :tongue:",
                                              "о себе :flushed:",
                                              "стоп :no_entry:");
			botAnswer.answer = result;
		}
		else if (this.currentStateGame == TruthOrDare.StatesGame.About) {
			currentUser.fsm.setState(this::truthOrDareGame);
			botAnswer.buttons = Arrays.asList("правда :zipper_mouth:",
                                              "действие :tongue:",
                                              "о себе :flushed:",
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

}
