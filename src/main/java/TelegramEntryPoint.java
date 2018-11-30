package src.main.java;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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


public class TelegramEntryPoint extends TelegramLongPollingBot{
	
	private String BOTS_TOKEN = System.getenv("BOTSTOKEN");
	private String BOTS_NAME = System.getenv("BOTSNAME");
	private Map<Long, Object> locks = new ConcurrentHashMap<Long, Object>();
	private static final UserRepository userRepository = new UserRepository();
	
	
	public static void main(String[] args) throws IOException {
		ApiContextInitializer.init();				
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		userRepository.initDatabase();		
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
            keyboardRow.add(new KeyboardButton(parseButton(labels.get(i))));
            keyboard.add(keyboardRow);
            
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
 
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		synchronized (locks.computeIfAbsent(message.getChatId(), k -> new Object())) 
		{
			if (message != null && message.hasText()) {
				userRepository.users.computeIfAbsent(message.getChatId(), k -> new Brain(userRepository, message.getChatId()));
				refreshUsernames(message);
				sendMsg(message, userRepository.users.get(message.getChatId()).reply(message.getText().toLowerCase()));
			}
		}
	}
	
	private void refreshUsernames(Message currentMessage) {
		if (currentMessage.getFrom().getUserName() != null) {
			userRepository.users.get(currentMessage.getChatId()).username =
					"@" + currentMessage.getFrom().getUserName();
		}
		else {
			userRepository.users.get(currentMessage.getChatId()).username =
					currentMessage.getFrom().getFirstName();
		}
	}
 
	private void sendMsg(Message message, BotAnswer botReply) {
		List<String> buttons = botReply.buttons;
		ReplyKeyboardRemove keyboardMurkup = new ReplyKeyboardRemove();
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setText(botReply.answer);
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

	private String parseButton(String text) {
		return EmojiParser.parseToUnicode(text);
	}
}