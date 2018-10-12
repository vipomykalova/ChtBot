package ChatBot;

import java.util.ListResourceBundle;

public class Dialog extends ListResourceBundle{

	public static final Dialog INSTANCE = new Dialog();
	
	static final Object[][] contents = {
			{"приветствие", "Привет :) Я бот, который умеет "
					+ "играть в \"Виселицу\" и в \"Правду или действие\"."
					+ " Во что хочешь поиграть?" + "\n"},
			{"прощание", "Обидно! Но если вдруг захочешь, то пиши мне :) Я буду ждать!" + "\n"},
			{"игроки", "Введи имена игроков (Напр.: Вика,Катя,Дима)" + "\n"},
			{"некорректный ввод", "Ничего не понял, повтори :)" + "\n"},
			{"начало", "Ну что ж, начинаем?" + "\n"},
		    };
	
	public Object[][] getContents() {
		return contents;
	    }

}
