package Tests; 

//import static junit.framework.Assert.assertEquals; 
//import static junit.framework.Assert.assertNull; 
import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

import ChatBot.Dialog;
import TruthOrDare.TruthOrDare; 

class TruthOrDareTest { 

	@Test 
	void askPlayerTest() { 
		TruthOrDare game = new TruthOrDare(); 
		game.gamers = new String[]{"Вика"}; 
		assertEquals(game.askPlayer(), game.gamers[0] + Dialog.INSTANCE.getString("что из")); 
	} 

	@Test 
	void taskForPlayerTest() { 
		TruthOrDare game = new TruthOrDare(); 
		assertNull(game.taskForPlayer("стоп")); 
		assertNull(game.taskForPlayer("nothing")); 
	} 
}