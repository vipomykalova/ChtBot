package Tests; 
 
import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

import ChatBot.Dialog;
import ChatBot.TruthOrDare;
import ChatBot.Brain;

class TruthOrDareTest { 

	@Test 
	void askPlayerTest() { 
		Brain brain = new Brain();
		TruthOrDare game = new TruthOrDare(brain); 
		game.gamers = new String[]{"Вика"}; 
		assertEquals(game.askPlayer(), game.gamers[0] + Dialog.INSTANCE.getString("что из"));
		assertEquals(game.truthOrDareAskPlayer("стоп"), Dialog.INSTANCE.getString("прощание"));
	} 
}