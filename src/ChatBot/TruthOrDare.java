package ChatBot;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public Map<String, List<String>> truthOrDareAskPlayer(String input) {
		this.checkState(input);
		Map<String, List<String>> curMap = new HashMap<>();
		List<String> curListButtons;
		if (this.currentStateGame == TruthOrDare.StatesGame.Stop) {
			currentUser.fsm.setState(currentUser::startMessage);
			curListButtons = Arrays.asList(":hand:"); 
			curMap.put(Dialog.INSTANCE.getString("прощание"), curListButtons);
			return curMap;
		}
		currentUser.fsm.setState(this::truthOrDareGame);
		curListButtons = Arrays.asList("правда :zipper_mouth:",
				                       "действие :tongue:",
				                       "о себе :flushed:",
				                       "стоп :no_entry:");
		curMap.put(this.askPlayer(), curListButtons);
		return curMap;
	}

	public Map<String, List<String>> truthOrDareGame(String input) {
		String result = this.taskForPlayer(input);
		Map<String, List<String>> curMap = new HashMap<>();
		List<String> curListButtons;
		if (this.currentStateGame == TruthOrDare.StatesGame.Correct) {
			currentUser.fsm.setState(this::truthOrDareAskPlayer);	
			curListButtons = Arrays.asList("OK :v:"); 
			curMap.put(result, curListButtons);
		}
		else if (this.currentStateGame == TruthOrDare.StatesGame.Incorrect) {
			currentUser.fsm.setState(this::truthOrDareGame);
			curListButtons = Arrays.asList("правда :zipper_mouth:",
                                           "действие :tongue:",
                                           "о себе :flushed:",
                                           "стоп :no_entry:");
			curMap.put(result, curListButtons);
		}
		else if (this.currentStateGame == TruthOrDare.StatesGame.About) {
			currentUser.fsm.setState(this::truthOrDareGame);
			curListButtons = Arrays.asList("правда :zipper_mouth:",
                                           "действие :tongue:",
                                           "о себе :flushed:",
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

}
