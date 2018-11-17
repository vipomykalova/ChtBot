package main.java;

import java.util.ListResourceBundle;
import com.vdurmont.emoji.EmojiParser;

public class Dialog extends ListResourceBundle{

	public static final Dialog INSTANCE = new Dialog();
	
	static final Object[][] contents = {
			{"приветствие", EmojiParser.parseToUnicode("Привет :blush: Я бот, который умеет "
					+ "играть в \"Виселицу\" и в \"Правду или действие\"."
					+ " Если хочешь еще раз увидеть это сообщение, напиши \"о себе\"."
					+ " Во что хочешь поиграть?:sparkles: \n")},
			{"расскажи", EmojiParser.parseToUnicode("Я бот, который умеет "
					+ "играть в \"Виселицу\" и в \"Правду или действие\"."
					+ " Если хочешь еще раз увидеть это сообщение, напиши \"о себе\". :sparkles: \n")},
			{"прощание", EmojiParser.parseToUnicode("Обидно! Но если вдруг захочешь, то пиши мне"
					+ " :blush: Я буду ждать!:sparkles: \n")},
			{"игроки", EmojiParser.parseToUnicode(":sparkles:Введи имена игроков (Напр.: Вика,Катя,Дима) \n")},
			{"некорректный ввод", EmojiParser.parseToUnicode("Ничего не понял, повтори :think: \n")},
			{"начало", EmojiParser.parseToUnicode("Ну что ж, начинаем?:sparkles: \n")},
			{"победа", EmojiParser.parseToUnicode("Молодец!:tada: \nСыграем еще?:smirk: \n")},
			{"проигрыш", EmojiParser.parseToUnicode("Ты проиграл :cry: \n")},
			{"еще", EmojiParser.parseToUnicode("Сыграем еще?:smirk: \n")},
			{"жизни", EmojiParser.parseToUnicode(":sparkles:Осталось жизней: ")},
			{"слово", EmojiParser.parseToUnicode("Загаданное слово: \n")},
			{"что из", EmojiParser.parseToUnicode(", правда или действие? :blush: \n")},
			{"пример ввода", EmojiParser.parseToUnicode("Некорректный ввод:thumbsdown: Примеры: \"Вика:виселица\","
					+ " \"1:правда или действие\":thumbsup: + \n")},
	};
	
	public Object[][] getContents() {
		return contents;
	    }

}
