package src.main.java;

import java.util.Arrays;

public class UserAboutGroupDialog {
	
	UsersBrain currentUser;
	String nameChat;
	private GroupRepository groupRepository;
	
	public UserAboutGroupDialog(UsersBrain brain, GroupRepository groupRepo) {
		currentUser = brain;
		groupRepository = groupRepo;
	}
 
	public BotAnswer check(String input)
	{
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("главное меню")) {
			currentUser.fsm.setState(currentUser::modeSelection);		
			botAnswer.buttons = Arrays.asList("одиночный :walking:",
	                                          "групповой :family:",
	                                          "о себе :flushed:");                   
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else {
			if (!groupRepository.isChatExist(input))
			{
				currentUser.fsm.setState(this::whatsNext);
				botAnswer.answer = Dialog.INSTANCE.getString("нет чата");
				botAnswer.buttons = Arrays.asList("ещё раз :smirk:",
	                                              "выход :door:"); 
			}
			else
			{
				nameChat = input;
				botAnswer.answer = Dialog.INSTANCE.getString("слово в чат");
				botAnswer.buttons = Arrays.asList();
				currentUser.fsm.setState(this::addWordInGroup);
			}
		}	
		return botAnswer;
	}
	
	public BotAnswer whatsNext(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("ещё раз")) {
			botAnswer.answer = Dialog.INSTANCE.getString("группа");
			botAnswer.buttons = Arrays.asList();
			currentUser.fsm.setState(this::check);
		}
		else if(input.startsWith("выход")) {
			botAnswer.buttons = Arrays.asList("одиночный :walking:",
                                              "групповой :family:",
                                              "о себе :flushed:");                   
            botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
            currentUser.fsm.setState(currentUser::modeSelection);
		}
		else {
			botAnswer.buttons = Arrays.asList("ещё раз :smirk:",
                                              "выход :door:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
			currentUser.fsm.setState(this::whatsNext);
		}
		return botAnswer;
	}
	
	public BotAnswer addWordInGroup(String input)
	{
		BotAnswer botAnswer = new BotAnswer();
		Long id = groupRepository.getData(nameChat);
		GroupsBrain chat = groupRepository.groupsChat.get(id);
	    chat.words.add(input);
	    currentUser.fsm.setState(this::wantMore);
	    botAnswer.answer = Dialog.INSTANCE.getString("есть слово в чат");
		botAnswer.buttons = Arrays.asList("ещё слова :smirk:",
				                          "другой чат :point_right:",
                                          "выход :door:"); 
		return botAnswer;
	}
	
	public BotAnswer wantMore(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("ещё слова")) {
			botAnswer.answer = Dialog.INSTANCE.getString("слово в чат");
			botAnswer.buttons = Arrays.asList();
			currentUser.fsm.setState(this::addWordInGroup);
		}
		else if(input.startsWith("другой чат")) {
			botAnswer.answer = Dialog.INSTANCE.getString("группа");
			botAnswer.buttons = Arrays.asList();
			currentUser.fsm.setState(this::check);
		}
		else if(input.startsWith("выход")) {
			botAnswer.buttons = Arrays.asList("одиночный :walking:",
                                              "групповой :family:",
                                              "о себе :flushed:");                   
            botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
            currentUser.fsm.setState(currentUser::modeSelection);
		}
		else {
			botAnswer.buttons = Arrays.asList("ещё слова :smirk:",
                                              "другой чат :point_right:",
                                              "выход :door:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
			currentUser.fsm.setState(this::wantMore);
		}
		return botAnswer;
	}
}