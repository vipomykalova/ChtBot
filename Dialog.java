
import java.util.ListResourceBundle;

public class Dialog extends ListResourceBundle{
	
	static final Object[][] contents = {
			{"привет", "Привет :) Я бот, который умеет "
					+ "играть в \"Виселицу\". Поиграем? Напиши \"да\" или \"нет\"." + "\n"},
			{"победа", "Поздравляю, ты победил!" + "\n"},
			{"проигрыш", "Упс, ты проиграл :(" + "\n"},
			{"еще", "Сыграем еще?" + "\n"},
			{"пока", "Обидно! Но если вдруг захочешь, то пиши мне :) Я буду ждать!" + "\n"},
		    };
	
	public Object[][] getContents() {
		return contents;
	    }

}
