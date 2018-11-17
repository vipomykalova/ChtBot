package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.java.Brain;
import main.java.Dialog;
import main.java.Hangman;

public class FSMTest {
	
	@Test
	void startMessageTest () {
		// тестим вывод пользователю стартого сообщения, т.е. начало работы
		Brain brain = new Brain();
		String result = brain.startMessage("привет").answer;
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
	}
	
	@Test
	void whatWeDoAfterStartMessage() {
		// тестим то, что после того, как бот поздоровается и предложит игру, а пользователь введет неккоректную игру,
		// он сообщит о некорректном вводе 
		Brain brain = new Brain();
		String result = brain.startMessage("привет").answer;
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
		String result = hangman.wantMore("нет").answer;
		assertEquals(Dialog.INSTANCE.getString("приветствие"),
				     brain.reply("я передумал, хочу играть").answer);
	}
	
	
}
