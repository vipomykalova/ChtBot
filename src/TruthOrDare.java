import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TruthOrDare {
	
	private HashMap<String, String> nameArchive;
	
	TruthOrDare() {
		nameArchive = new HashMap<>();
		nameArchive.put("правда", "ArchiveTruth");
		nameArchive.put("действие", "ArchiveDare");
	}
	
	public boolean endOfGame;
	
	public void game() {
		
		endOfGame = false;
		Random rnd = new Random();
		String currentGamer = "";
		String task = "";
		String answer = "";
		
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("игроки"));
		String[] gamers = InOut.INSTANCE.input().split(",");
					
		while (!endOfGame) {
			currentGamer = gamers[rnd.nextInt(gamers.length)];
			InOut.INSTANCE.output(currentGamer + ", правда или действие? :)" + "\n");
			answer = InOut.INSTANCE.input();
			if (answer.equals("стоп")) {
				endOfGame = true;
			}
			else {
				if (!answer.equals("правда") && !answer.equals("действие")) {
					InOut.INSTANCE.output("Что, прости? :)" + "\n");
					answer = InOut.INSTANCE.input();
				}
				task = TaskMaker.newTask(nameArchive.get(answer));
				InOut.INSTANCE.output(task + "\n");
				while(InOut.INSTANCE.input().isEmpty()) {
					
				}
			}
		}
	}

}
