package test.java;
 
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.Dialog;
import main.java.TruthOrDare;
import main.java.Brain;

class TruthOrDareTest { 

	@Test 
	void askPlayerTest() { 
        //тестим то, что бот ответит после ввода игроков и нажатия "стоп"
		Brain brain = new Brain();
		TruthOrDare game = new TruthOrDare(brain); 
		game.gamers = new String[]{"Вика"}; 
		assertEquals(game.askPlayer(), game.gamers[0] + Dialog.INSTANCE.getString("что из"));
		assertEquals(game.truthOrDareAskPlayer("стоп").answer,
				     Dialog.INSTANCE.getString("прощание"));
	} 
	
	@Test
	void getTaskFromArchivesTest() {
		//тестим то, что после выбора правды или действия будут даны именно они
		Brain brain = new Brain();
		TruthOrDare game = new TruthOrDare(brain); 
		game.nameArchive.put("правда", "TruthTest");
		game.nameArchive.put("действие", "DareTest");
		assertEquals(game.taskForPlayer("правда"), "тебе нравится Оксана?" + "\n");
		assertEquals(game.taskForPlayer("действие"), "поцелуй Оксану" + "\n");
		
	}
	
	@Test
	void checkAskPlayers() {
		//тестим то, что всех участников игры бот спросит
		Brain brain = new Brain();
		TruthOrDare game = new TruthOrDare(brain);
		game.gamers = new String[]{"Вика", "Оксана"};
		String nameGamer1 = "";
		String nameGamer2 = "";
		for (int i = 0; i < 100; i++) {
			if (game.askPlayer().startsWith("Оксана")) {
				nameGamer1 = "Оксана";
			}
			if (game.askPlayer().startsWith("Вика")) {
				nameGamer2 = "Вика";
			}
		}
		assertEquals(nameGamer1, "Оксана");
		assertEquals(nameGamer2, "Вика");
	}
}