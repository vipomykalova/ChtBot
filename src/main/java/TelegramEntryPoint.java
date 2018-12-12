package src.main.java;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import com.vdurmont.emoji.EmojiParser;


public class TelegramEntryPoint extends TelegramLongPollingBot{
	
	private String BOTS_TOKEN = System.getenv("BOTSTOKEN");
	private String BOTS_NAME = System.getenv("BOTSNAME");
	private Map<Integer, Object> locks = new ConcurrentHashMap<Integer, Object>();
	private Map<Long, GroupsBrain> groupsChat = new ConcurrentHashMap<Long, GroupsBrain>();
	private static UserRepository userRepository;
	private Update previousMessage = new Update();
	WorkerWithListGroups worker = new WorkerWithListGroups();
	
	private static final Initialization initializer = new Initialization();
	
	public static void main(String[] args) throws IOException {
		ApiContextInitializer.init();				
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		initializer.initDatabase();		
		userRepository = new UserRepository(initializer);
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
	
	public AnswerInlineQuery convertResultToResponse(InlineQuery inlineQuery)
	{
		System.out.println("begin");
		AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
		answerInlineQuery.setInlineQueryId(inlineQuery.getId());
		//answerInlineQuery.setCacheTime(5000);
		InputTextMessageContent messageContent = new InputTextMessageContent();
		messageContent.disableWebPagePreview();
		messageContent.enableMarkdown(true);
		messageContent.setMessageText(EmojiParser.parseToUnicode("Ну что, попробуйте угадать:smiling_imp:"));
		InlineQueryResultArticle article = new InlineQueryResultArticle();
		article.setInputMessageContent(messageContent);
		article.setId("pickleBot");
		article.setTitle(EmojiParser.parseToUnicode("Добавим словечко:hourglass_flowing_sand:"));
		answerInlineQuery.setResults(article);
		return answerInlineQuery;
	}
	
 
	@Override
	public void onUpdateReceived(Update update) {
		synchronized (locks.computeIfAbsent(update.getUpdateId(), k -> new Object())) {
			if (update.hasInlineQuery()) {
				InlineQuery inlineQuery = update.getInlineQuery();
				String query = inlineQuery.getQuery();
				System.out.println(query);
				if (!query.isEmpty()) {
					try {
						System.out.println("end");
						previousMessage = update;
						execute(convertResultToResponse(inlineQuery));
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}
			}
			else if (update.hasMessage()) {
				Message message = update.getMessage();
				if (message != null && message.hasText()) {
					System.out.println(message);
					if (message.getChat().isGroupChat())
					{
						groupsChat.computeIfAbsent(message.getChat().getId(), k -> new GroupsBrain(worker));
						if (previousMessage.hasInlineQuery()) {
							System.out.println("добавили в словарь: " + previousMessage.getInlineQuery().getQuery());							
							worker.addWord(groupsChat.get(message.getChat().getId()), previousMessage.getInlineQuery().getQuery());
							previousMessage = new Update();
						}
						else {
							sendMsg(message, groupsChat.get(message.getChat().getId()).reply(message.getText().toLowerCase()));
						}
					}
					else {
					    userRepository.users.computeIfAbsent(message.getChatId(),
					    		k -> new Brain(userRepository, message.getChatId()));
					    refreshUsernames(message);
					    sendMsg(message, userRepository.users.get(
								message.getChatId()).reply(message.getText().toLowerCase()));
					}
			   }
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
		if (botReply != null) {
			List<String> buttons = botReply.buttons;
			ReplyKeyboardRemove keyboardMurkup = new ReplyKeyboardRemove();
			SendMessage sendMessage = new SendMessage();
			sendMessage.enableMarkdown(true);
			sendMessage.setChatId(message.getChatId().toString());
			sendMessage.setText(botReply.answer);
			try {
				if (!buttons.isEmpty() && botReply != null) {
					setButtons(sendMessage, buttons);
					execute(sendMessage);
				}
				else if (botReply != null) {
					execute(sendMessage.setReplyMarkup(keyboardMurkup));
				}
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}

	}

	private String parseButton(String text) {
		return EmojiParser.parseToUnicode(text);
	}
}