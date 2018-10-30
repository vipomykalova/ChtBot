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
		String result = brain.startMessage("привет").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
	}
	
	@Test
	void whatWeDoAfterStartMessage() {
		// тестим то, что после того, как бот поздоровается и предложит игру, а пользователь введет неккоректную игру,
		// он сообщит о некорректном вводе 
		Brain brain = new Brain();
		String result = brain.startMessage("привет").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("некорректный ввод"),
				     brain.reply("города").keySet().toArray()[0].toString());
	}
	
	@Test
	void gameSelectionTest() {
		// тестим состояние автомата, отвечающее за этап выбора игры
		Brain brain = new Brain();
		String result = brain.gameSelection("виселица").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("начало"), result);
		result = brain.gameSelection("правда или действие").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("начало"), result);
		result = brain.gameSelection("о себе").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("приветствие"), result);
		result = brain.gameSelection("что-то другое").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("некорректный ввод"), result);
	}
	
	@Test
	void wantMoreTest() {
		// тестим то, что после отказа в вопросе хочу ли я сыграть, автомат перейдет к стартовому сообщению
		Brain brain = new Brain();
		Hangman hangman = new Hangman(brain);
		String result = hangman.wantMore("нет").keySet().toArray()[0].toString();
		assertEquals(Dialog.INSTANCE.getString("приветствие"),
				     brain.reply("я передумал, хочу играть").keySet().toArray()[0].toString());
	}
	
	
}
