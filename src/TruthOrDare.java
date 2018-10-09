import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TruthOrDare {

	private enum StatesGame {
		Correct, Incorrect, Stop
	}
	public StatesGame currentStateGame;
	private HashMap<String, String> nameArchive;
	String[] gamers;
	
	TruthOrDare() {
		nameArchive = new HashMap<>();
		nameArchive.put("правда", "Truth");
		nameArchive.put("действие", "Dare");
	}

	public String taskforPlayer(String answer){
		if(answer.equals("стоп")) {
			currentStateGame = StatesGame.Stop;
			return "";
		}
		if (!answer.equals("правда") && !answer.equals("действие")) {
			currentStateGame = StatesGame.Incorrect;
			return "";
		} else {
			currentStateGame = StatesGame.Correct;
			return TaskMaker.newTask(nameArchive.get(answer));
		}
	}

	public String askPlayer() {
		Random rnd = new Random();
		String currentGamer = gamers[rnd.nextInt(gamers.length)];
		return currentGamer + ", правда или действие? :)";
	}

	public void parseNames(String names) {
		gamers = names.split(",");
	}

}
