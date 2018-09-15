import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DialogTest {

	@Test
	void startMessageTest() {
		assertEquals(Dialog.sendStartMessage(), "Привет :) Я твой новый чат-бот. "
				+ "Поговорим? Чтобы вернуться к приветствию, "
				+ "напиши \"О себе\".");
	}
	
	@Test
	void talkToMeTest() {
		assertEquals(Dialog.sendTalkToMe(), "Ну поговори со мной!");
	}

}
