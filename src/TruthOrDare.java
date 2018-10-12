import java.util.HashMap;
import java.util.Random;

public class TruthOrDare {

	public enum StatesGame {
		Correct, Incorrect, Stop
	}
	
	Random rnd = new Random();
	public StatesGame currentStateGame;
	public HashMap<String, String> nameArchive;
	public String[] gamers;
	
	TruthOrDare() {
		nameArchive = new HashMap<>();
		nameArchive.put("правда", "Truth");
		nameArchive.put("действие", "Dare");
	}

	public String taskForPlayer(String answer){
		checkState(answer);
		switch(currentStateGame) {
		case Correct:
			return TaskMaker.newTask(nameArchive.get(answer)) + "\n";
		case Incorrect:
			return null;
		case Stop:
			return null;
		}
		return null;
	}
	
	public void checkState(String answer) {
		if (answer.equals("стоп")) {
			currentStateGame = StatesGame.Stop;
		} else if (answer.equals("правда") || answer.equals("действие")) {
			currentStateGame = StatesGame.Correct;
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

}
