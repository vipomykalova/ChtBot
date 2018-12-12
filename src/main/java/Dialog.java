package src.main.java;

import java.util.ListResourceBundle;
import com.vdurmont.emoji.EmojiParser;

public class Dialog extends ListResourceBundle{

	public static final Dialog INSTANCE = new Dialog();
	
	static final Object[][] contents = {
			{"приветствие", EmojiParser.parseToUnicode("Привет :blush: Я бот, который умеет "
					+ "играть в \"Виселицу\" и в \"Правду или действие\"."
					+ " Причем в \"Виселицу\" как с тобой лично, так и в твоем чатике, с твоими друзьями! "
					+ " Чтобы опробовать второй вариант, - просто добавь меня в чатик. "
					+ " Каждый из участников может загадывать слова, обратясь ко мне через @ImPickleBot \"слово\"." 
					+ " Если не хотите придумывать слова, я буду загадывать свои :smirk:."
					+ " Как наиграетесь, просто удалите меня из чатика.")},
			{"расскажи", EmojiParser.parseToUnicode("Я бот, который умеет "
					+ "играть в \"Виселицу\" и в \"Правду или действие\"."
					+ " Причем в \"Виселицу\" как с тобой лично, так и в твоем чатике, с твоими друзьями! "
					+ "Выйди в главное меню и выбери групповой режим!"
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
			{"что редактировать", EmojiParser.parseToUnicode("Какую игру будем редактировать?:sparkles:\n")},
			{"виселица", EmojiParser.parseToUnicode("Что хочешь сделать: добавить слово в архив или удалить?:sparkles:\n")},
			{"правда или действие", EmojiParser.parseToUnicode("Что хочешь сделать: добавить задание в архив или удалить?:sparkles:\n")},
			{"удалить", EmojiParser.parseToUnicode("Введи задание, которое хочешь удалить:sparkles:\n")},
			{"что удалить", EmojiParser.parseToUnicode("Что будем редактировать: правду или действие?:sparkles:\n")},
			{"что добавить", EmojiParser.parseToUnicode("Что будем редактировать: правду или действие?:sparkles:\n")},
			{"добавить", EmojiParser.parseToUnicode("Введи задание, которое хочешь добавить:sparkles:\n")},
			{"задание уже есть", EmojiParser.parseToUnicode("Но такое задание уже есть:point_up:\n")},
			{"добавлено", EmojiParser.parseToUnicode("Добавил:muscle:\n")},
			{"ошибка", EmojiParser.parseToUnicode("Что-то пошло не так:sweat:\n")},
			{"задание отсутствует", EmojiParser.parseToUnicode("Такого задания нет, че его удалять то:sheep:\n")},
			{"удалено", EmojiParser.parseToUnicode("Всё, этого задания больше нет:muscle:\n")},
			{"победа в группе", EmojiParser.parseToUnicode("ЕЕЕ, победа:sunglasses:\n")},
			{"еще в группе", EmojiParser.parseToUnicode("Поехали дальше?:boom:\n")},
			{"проигрыш в группе", EmojiParser.parseToUnicode("Ууу, ацтой, вы проиграли:thumbsdown:\n")},
			{"играем", EmojiParser.parseToUnicode("Во что хочешь поиграть?:yum:")}, 
	};
	
	public Object[][] getContents() {
		return contents;
	    }

}
