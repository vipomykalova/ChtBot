package src.test.java; 

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

import com.vdurmont.emoji.EmojiParser;

import src.main.java.Dialog;
import src.main.java.Hangman;
import src.main.java.MakerOfStatistics;
import src.main.java.UserRepository;
import src.main.java.Brain;

public class HangmanTest { 

	@Test
	void correctResultTest() { 
		//тестим верные ответы пользователя: случай угаданной буквы и победу
		Brain brain = new Brain();
		Hangman game = new Hangman(brain); 
		game.word = "право"; 
	    game.setWord(); 
		assertEquals(game.currentResult("а"), Dialog.INSTANCE.getString("жизни") +
				     "10\n__а__\n");
		game.currentResult("п");
		game.currentResult("р");
		game.currentResult("в");
		assertEquals(game.currentResult("о"), Dialog.INSTANCE.getString("слово") + game.word + "\n" +
				   Dialog.INSTANCE.getString("победа"));
	} 
	
	@Test
	void incorrectResultTest() {
		//тестим неверные ответы пользователя: случай неугаданной буквы и проигрыш
		Brain brain = new Brain();
		Hangman game = new Hangman(brain); 
		game.word = "право"; 
	    game.setWord(); 
	    assertEquals(game.currentResult("к"), Dialog.INSTANCE.getString("жизни") +
	    		     game.life.lives + "\n" + game.currentWord() + "\n");
	    for (int i=0; i < 8; i++) {
	    	game.currentResult("к");
	    }
	    assertEquals(game.currentResult("к"), Dialog.INSTANCE.getString("проигрыш") + 
	    		     Dialog.INSTANCE.getString("слово") + game.word + "\n" +
	    		     Dialog.INSTANCE.getString("еще"));
	}
	
	@Test
	void stopInputTest() {
		//тестим ввод "стоп" от пользователя -> завершение игры
		Brain brain = new Brain();
		Hangman game = new Hangman(brain); 
		game.currentResult("стоп");
		assertEquals(game.currentStateGame, Hangman.StatesGame.Stop);
	}
	
	@Test
	void statisticsForOneUserTest() {
		//тестим работу статистики для одного юзера: еще не играл, когда уже сыграл
		Brain brain = new Brain();
		brain.username = "Вика";
		UserRepository.users.put(Long.valueOf(1), brain);
		Hangman game = new Hangman(brain); 
		String result = game.currentResult("статистика");
		assertEquals(result, "Никто еще не играл, увы..\n");
		game.word = "а"; 
	    game.setWord();
	    game.currentResult("а");
	    result = game.wantMore("статистика").answer;
	    assertEquals(result, EmojiParser.parseToUnicode("Вика :heavy_plus_sign::1 :heavy_minus_sign::0\n"));
	}
	
	@Test
	void statisticsForSeveralUsersTest() {
		//тестим работу статистики для нескольких юзеров: не играл никто, играл один, играли несколько
		Brain firstUser = new Brain();
		firstUser.username = "Вика";
		Brain secondUser = new Brain();
		secondUser.username = "Оксана";
		UserRepository.users.put(Long.valueOf(1), firstUser);
		UserRepository.users.put(Long.valueOf(2), secondUser);
		Hangman gameForFirstUser = new Hangman(firstUser);
		Hangman gameForSecondUser = new Hangman(secondUser);
		String result = gameForFirstUser.currentResult("статистика");
		assertEquals(result, "Никто еще не играл, увы..\n");
		//начал играть первый юзер - победа
		gameForFirstUser.word = "як";
		gameForFirstUser.setWord();
		gameForFirstUser.currentResult("я");
		gameForFirstUser.currentResult("к");
		result = gameForFirstUser.wantMore("статистика").answer;
		assertEquals(result, EmojiParser.parseToUnicode("Вика :heavy_plus_sign::1 "
				                                        + ":heavy_minus_sign::0\n"));
		//начал играть второй юзер - проигрыш
		gameForSecondUser.word = "око";
		gameForSecondUser.setWord();
		for (int i=0; i < 10; i++) {
			gameForSecondUser.currentResult("с");
	    }
		result = gameForSecondUser.wantMore("статистика").answer;
		assertEquals(result,
				     EmojiParser.parseToUnicode("Вика :heavy_plus_sign::1 :heavy_minus_sign::0\n"
				     		                    + "Оксана :heavy_plus_sign::0 :heavy_minus_sign::1\n"));
	}
	
	@Test
	void sortedStatisticsTest() {
		//тестим правильность сортировки статистики (в порядке убывания отгаданных слов)
		Brain firstUser = new Brain();
		firstUser.username = "Вика";
		firstUser.wins = 5;
		firstUser.fails = 2;
		Brain secondUser = new Brain();
		secondUser.username = "Оксана";
		secondUser.wins = 4;
		secondUser.fails = 1;
		Brain thirdUser = new Brain();
		thirdUser.username = "Кэйт";
		thirdUser.wins = 3;
		thirdUser.fails = 0;
		UserRepository.users.put(Long.valueOf(1), firstUser);
		UserRepository.users.put(Long.valueOf(2), secondUser);
		UserRepository.users.put(Long.valueOf(3), thirdUser);
		String result = MakerOfStatistics.getStatistics();
		assertEquals(result, EmojiParser.parseToUnicode("Вика :heavy_plus_sign::5 :heavy_minus_sign::2\n"
				    		 + "Оксана :heavy_plus_sign::4 :heavy_minus_sign::1\n"
				    		 + "Кэйт :heavy_plus_sign::3 :heavy_minus_sign::0\n"));
		
	}
}