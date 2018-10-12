package Tests; 

//import static junit.framework.Assert.assertEquals; 
//import static junit.framework.Assert.assertNull; 
import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

import ChatBot.Dialog;
import Hangman.Hangman; 

public class HangmanTest { 

	@Test 
	void currentResultTest() { 
		Hangman game = new Hangman(); 
		game.word = "право"; 
		assertNull(game.currentResult("стоп")); 
		assertEquals(game.currentResult("п"), game.word + "\n" + Dialog.INSTANCE.getString("победа")); 
	} 
}