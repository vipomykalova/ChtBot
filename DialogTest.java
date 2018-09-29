import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DialogTest {

	@Test
	void moreMessageTest() {
		Dialog curDialog = new Dialog();
		assertEquals(curDialog.getString("еще"), "Сыграем еще?");
	}
	
	@Test
	void startMessageTest() {
		Dialog curDialog = new Dialog();
		assertEquals(curDialog.getString("привет"), "Привет :) Я бот, который умеет "
				+ "играть в \"Виселицу\". Поиграем? Напиши \"да\" или \"нет\".");
	}

}
