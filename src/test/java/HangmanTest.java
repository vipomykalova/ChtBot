package test.java; 

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

import main.java.Dialog;
import main.java.Hangman;
import main.java.Brain;

public class HangmanTest { 

	@Test 
	void correctResultTest() { 
		//тестим верные ответы пользователя; случай угаданной буквы и победу
		Brain brain = new Brain();
		Hangman game = new Hangman(brain); 
		game.word = "право"; 
	    game.setWord(); 
		assertEquals(game.currentResult("а"), Dialog.INSTANCE.getString("жизни") +
				     game.life.lives + "\n" + game.currentWord() + "\n");
		game.currentResult("п");
		game.currentResult("р");
		game.currentResult("в");
		assertEquals(game.currentResult("о"), Dialog.INSTANCE.getString("слово") + game.word + "\n" +
				   Dialog.INSTANCE.getString("победа"));
	} 
	
	@Test
	void incorrectResultTest() {
		//тестим неверные ответы пользователя; случай неугаданной буквы и проигрыш
		Brain brain = new Brain();
		Hangman game = new Hangman(brain); 
		game.word = "право"; 
	    game.setWord(); 
	    assertEquals(game.currentResult("к"), Dialog.INSTANCE.getString("жизни") +
	    		     game.life.lives + "\n" + game.currentWord() + "\n");
	    String incorrectResult;
	    for (int i=0; i < 8; i++) {
	    	incorrectResult = game.currentResult("к");
	    }
	    assertEquals(game.currentResult("к"), Dialog.INSTANCE.getString("проигрыш") + 
	    		     Dialog.INSTANCE.getString("слово") + game.word + "\n" +
	    		     Dialog.INSTANCE.getString("еще"));
	}
	
	@Test
	void stopInputTest() {
		//тестим ввод "стоп" от пользоввателя -> завершение игры
		Brain brain = new Brain();
		Hangman game = new Hangman(brain); 
		game.currentResult("стоп");
		assertEquals(game.currentStateGame, Hangman.StatesGame.Stop);
	}
}