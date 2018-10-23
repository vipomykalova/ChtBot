package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ChatBot.Brain;
import ChatBot.Dialog;
import ChatBot.Hangman;

public class FSMTest {
	
	@Test
	void startMessageTest () {
		// тестим вывод пользователю стартого сообщения, т.е. начало работы
		Brain brain = new Brain();
		String result = brain.startMessage("привет");
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
	}
	
	@Test
	void whatWeDoAfterStartMessage() {
		// тестим то, что после того, как бот поздоровается и предложит игру, а пользователь введет неккоректную игру,
		// он сообщит о некорректном вводе 
		Brain brain = new Brain();
		String result = brain.startMessage("привет");
		assertEquals(Dialog.INSTANCE.getString("некорректный ввод"), brain.reply("города"));
	}
	
	@Test
	void gameSelectionTest() {
		// тестим состояние автомата, отвечающее за этап выбора игры
		Brain brain = new Brain();
		String result = brain.gameSelection("виселица");
		assertEquals(Dialog.INSTANCE.getString("начало"), result);
		result = brain.gameSelection("правда или действие");
		assertEquals(Dialog.INSTANCE.getString("начало"), result);
		result = brain.gameSelection("о себе");
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
		result = brain.gameSelection("что-то другое");
		assertEquals(Dialog.INSTANCE.getString("некорректный ввод"), result);
	}
	
	@Test
	void wantMoreTest() {
		// тестим то, что после отказа в вопросе хочу ли я сыграть, автомат перейдет к стартовому сообщению
		Brain brain = new Brain();
		Hangman hangman = new Hangman(brain);
		String result = hangman.wantMore("нет");
		assertEquals(Dialog.INSTANCE.getString("приветствие"), brain.reply("я передумал, хочу играть"));
	}
	
	
}
