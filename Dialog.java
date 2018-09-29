
import java.util.ListResourceBundle;

public class Dialog extends ListResourceBundle{
	
	static final Object[][] contents = {
			{"привет", "Привет :) Я бот, который умеет "
					+ "играть в \"Виселицу\". Поиграем? Напиши \"да\" или \"нет\"."},
			{"победа", "Поздравляю, ты победил!"},
			{"проигрыш", "Упс, ты проиграл :("},
			{"еще", "Сыграем еще?"},
			{"пока", "Обидно! Но если вдруг захочешь, то пиши мне :) Я буду ждать!"},
		    };
	
	public Object[][] getContents() {
		return contents;
	    }

}
