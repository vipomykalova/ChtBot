package ChatBot;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import com.vdurmont.emoji.EmojiParser;

import ChatBot.Brain;
import ChatBot.Dialog; 

public class TelegramEntryPoint extends TelegramLongPollingBot{
	
	private static String BOTS_TOKEN = System.getenv("BOTSTOKEN");
	private static String BOTS_NAME = System.getenv("BOTSNAME");
	private static Map<Long, Brain> users = new HashMap<Long, Brain>();
	private static Message message;
	
	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new TelegramEntryPoint());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
	@Override
	public String getBotUsername() {
		return BOTS_NAME;
	}
 
	@Override
	public String getBotToken() {
		return BOTS_TOKEN;
	}
	
	public synchronized void setButtons(SendMessage sendMessage, List<String> labels) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        
        for (int i = 0; i < labels.size(); i++) {
        	KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(labels.get(i)));
            keyboard.add(keyboardRow);
            
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
 
	@Override
	public void onUpdateReceived(Update update) {
		message = update.getMessage();
		users.putIfAbsent(message.getChatId(), new Brain());
		if (message != null && message.hasText()) {
			sendMsg(message, users.get(message.getChatId()).reply(message.getText().toLowerCase()));
		}
	}
 
	private void sendMsg(Message message, String text) {
		List<String> buttons = setButtonsList(text);
		ReplyKeyboardRemove keyboardMurkup = new ReplyKeyboardRemove();
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setText(text);
		try {
			if (!buttons.isEmpty()) {
				setButtons(sendMessage, buttons);
				execute(sendMessage);
			}
			else {
				execute(sendMessage.setReplyMarkup(keyboardMurkup));
			}
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> setButtonsList(String text) {
		List<String> buttons = new ArrayList<>();
		if (text.equals(Dialog.INSTANCE.getString("приветствие"))) {
			addButton(buttons, "правда или действие :underage:");
			addButton(buttons, "виселица :detective:");
			addButton(buttons, "о себе :flushed:");
		}
		else if (text.equals(Dialog.INSTANCE.getString("начало"))) {
			addButton(buttons, "ДА:fire:");
		}
		else if (text.endsWith(Dialog.INSTANCE.getString("еще")) ||
				 text.endsWith(Dialog.INSTANCE.getString("победа"))) {
			addButton(buttons, "ДА:fire:");
			addButton(buttons, "НЕТ:hankey:");
		}
		else if (text.endsWith(Dialog.INSTANCE.getString("что из"))) {
			addButton(buttons, "правда :zipper_mouth:");
			addButton(buttons, "действие :tongue:");
			addButton(buttons, "о себе :flushed:");
			addButton(buttons, "стоп :no_entry:");
		}
		else if (text.startsWith(Dialog.INSTANCE.getString("жизни")) ||
				 text.startsWith("-")) {
			addButton(buttons, "о себе :flushed:");
			addButton(buttons, "стоп :no_entry:");
		}
		else if (!message.getText().startsWith("действие или действие") &&
				(message.getText().startsWith("правда") || message.getText().startsWith("действие"))) {
			addButton(buttons, "OK :v:");
		}
		else if (text.equals(Dialog.INSTANCE.getString("расскажи"))) {
			if (users.get(message.getChatId()).currentGame.equals("правда или действие")) {
				addButton(buttons, "правда :zipper_mouth:");
				addButton(buttons, "действие :tongue:");
				addButton(buttons, "о себе :flushed:");
				addButton(buttons, "стоп :no_entry:");
			}
			else {
				addButton(buttons, "о себе :flushed:");
				addButton(buttons, "стоп :no_entry:");
			}
		}
		return buttons;
	}

	private void addButton(List<String> buttons, String text) {
		buttons.add(EmojiParser.parseToUnicode(text));
	}
}
