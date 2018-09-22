import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DialogTest {

	@Test
	void startMessageTest() {
		Dialog curDialog = new Dialog();
		assertEquals(curDialog.sendStartMessage(), "Привет :) Я бот, который умеет "
				+ "играть в \"Виселицу\". Поиграем? Напиши \"да\" или \"нет\".");
	}
	
	@Test
	void talkToMeTest() {
		Dialog curDialog = new Dialog();
		assertEquals(curDialog.sendWinnerMessage(), "Поздравляю, ты победил!");
	}

}
