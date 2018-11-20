package src.test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import src.main.java.Brain;
import src.main.java.Dialog;
import src.main.java.Hangman;
import src.main.java.UserRepository;

public class FSMTest {
	
	@Test
	void startMessageTest () {
		// тестим вывод пользователю стартого сообщения, т.е. начало работы
		Brain brain = new Brain();
		String result = brain.reply("привет").answer;
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
	}
	
	@Test
	void whatWeDoAfterStartMessage() {
		// тестим то, что после того, как бот поздоровается и предложит игру, а пользователь введет неккоректную игру,
		// он сообщит о некорректном вводе 
		Brain brain = new Brain();
		brain.reply("привет");
		assertEquals(Dialog.INSTANCE.getString("некорректный ввод"),
				     brain.reply("города").answer);
	}
	
	@Test
	void gameSelectionTest() {
		// тестим состояние автомата, отвечающее за этап выбора игры
		Brain brain = new Brain();
		String result = brain.gameSelection("виселица").answer;
		assertEquals(Dialog.INSTANCE.getString("начало"), result);
		result = brain.gameSelection("правда или действие").answer;
		assertEquals(Dialog.INSTANCE.getString("начало"), result);
		result = brain.gameSelection("о себе").answer;
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
		result = brain.gameSelection("что-то другое").answer;
		assertEquals(Dialog.INSTANCE.getString("некорректный ввод"), result);
	}
	
	@Test
	void wantMoreTest() {
		// тестим то, что после отказа в вопросе хочу ли я сыграть, автомат перейдет к стартовому сообщению
		Brain brain = new Brain();
		Hangman hangman = new Hangman(brain);
		hangman.wantMore("нет");
		assertEquals(Dialog.INSTANCE.getString("приветствие"),
				     brain.reply("я передумал, хочу играть").answer);
	}
	
	@Test
	void getStatisticsTest() {
		//тестим то, что после вывода статистики или о себе автомат вернется в предыдущее состояние
		Brain brain = new Brain();
		brain.username = "Вика";
		UserRepository.users.put(Long.valueOf(1), brain);
		Hangman game = new Hangman(brain); 
		game.word = "як"; 
	    game.setWord();
	    game.currentResult("я");
	    assertEquals(game.currentResult("статистика"), "Никто еще не играл, увы..\n");
	    assertEquals(game.currentResult("о себе"), Dialog.INSTANCE.getString("расскажи")); 
	    assertEquals(game.currentResult("к"), Dialog.INSTANCE.getString("слово") + game.word + "\n" +
				   Dialog.INSTANCE.getString("победа"));
	}
	
}
