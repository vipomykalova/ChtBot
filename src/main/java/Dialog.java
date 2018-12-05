package src.main.java;

import java.util.ListResourceBundle;
import com.vdurmont.emoji.EmojiParser;

public class Dialog extends ListResourceBundle{

	public static final Dialog INSTANCE = new Dialog();
	
	static final Object[][] contents = {
			{"приветствие", EmojiParser.parseToUnicode("Привет :blush: Я бот, который умеет "
					+ "играть в \"Виселицу\" и в \"Правду или действие\"."
					+ " Причем в \"Виселицу\" как с тобой лично, так и в твоем чатике, с твоими друзьями! "
					+ " Чтобы опробовать второй вариант, - просто добавь меня в чатик и напиши слово \"старт\". "
					+ " Каждый из участников может писать мне слово в личку, а я буду его загадывать!" 
					+ " Ну что, какой режим выбираем? Только ты и я... или в дружной компании? :)")},
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
			{"привет для группы", EmojiParser.parseToUnicode("Йоу, всем привет!:v: Я призван в ваш чат для того, чтобы " + 
					                                         "вы поиграли в Виселицу. Причем каждый из вас может " + 
					                                         "загадывать слова своим друзьям, для этого пишите " + 
					                                         "мне в личку) Если захотите закончить игру, просто введите \"стоп\".\n" +
					                                         "Как будете готовы, напишите что-нибудь сюда!:yum:\n")},
			{"победа в группе", EmojiParser.parseToUnicode("ЕЕЕ, победа:sunglasses:\n")},
			{"еще в группе", EmojiParser.parseToUnicode("Поехали дальше?:boom:\n")},
			{"проигрыш в группе", EmojiParser.parseToUnicode("Ууу, ацтой, вы проиграли:thumbsdown:\n")},
			{"пока для группы", EmojiParser.parseToUnicode("Наигрались что ли, хехе:smiley:\n" +
			                                               "Ладно, как только захотите со мной поиграть, - пишите \"старт\":sparkles:")},
			{"группа", EmojiParser.parseToUnicode("Так-с, в каком чатике слова загадывать будем? Чтобы активировать меня, не "
					                              + "забудь ввести \"старт\"."
					                              + " Если еще не добавили меня туда, то добавь обязательно!"
					                              + " Иначе как играть будем?!:point_up:")},
			{"играем", EmojiParser.parseToUnicode("Во что хочешь поиграть?:yum:")}, 
			{"нет чата", EmojiParser.parseToUnicode("Такого чатика нет в моем списке. Если хотите играть,"
			        + "добавьте меня уже хоть кто-нибудь!:scream:")},
			{"слово в чат", EmojiParser.parseToUnicode("Какое слово будем загадывать твоим друзьям?:blush:")},
			{"есть слово в чат", EmojiParser.parseToUnicode("Супер, как только закончатся предыдущие задания, я закину твоё:wink:")},
	};
	
	public Object[][] getContents() {
		return contents;
	    }

}
