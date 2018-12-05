package src.main.java;

import java.util.Arrays;

public class UsersBrain {

	private AdminChecker adminChecker = new AdminChecker();
	private AdminDialog adminDialog = new AdminDialog(this);
	private Hangman currentHangman;
	private TruthOrDare currentTruthOrDare;
	private UserRepository userRepository;
	private GroupRepository groupRepository;
	private Boolean isAdmin;
	public FSM fsm = new FSM();
	public String currentGame;
	public String username;
	public Long chatId;
	
	public UsersBrain() {
		fsm.setState(this::startMessage);
	}

	public UsersBrain(UserRepository userRepo, Long id, GroupRepository groupRepo) {
		chatId = id;
		userRepository = userRepo;
		groupRepository = groupRepo;
		isAdmin = adminChecker.checkAdmin(chatId);
		fsm.setState(this::startMessage);
	}
	
	public BotAnswer startMessage(String input) {
		fsm.setState(this::modeSelection);		
		BotAnswer botAnswer = new BotAnswer();
		botAnswer.buttons = Arrays.asList("одиночный :walking:",
                                          "групповой :family:",
                                          "о себе :flushed:");                   
		botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		return botAnswer;
	}
	
	public BotAnswer modeSelection(String input)
	{   BotAnswer botAnswer = new BotAnswer();
	
	    if (input.startsWith("одиночный"))
	    {
		    fsm.setState(this::gameSelection);
		    if (isAdmin) {
			    botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                                  "виселица :detective:",
                                                  "редактировать :pencil2:",
                                                  "о себе :flushed:",
                                                  "главное меню :door:");
		    }
		    else { 
			    botAnswer.buttons = Arrays.asList("правда или действие :underage:",
					                              "виселица :detective:",
					                              "о себе :flushed:",
                                                  "главное меню :door:"); 
		    } 
		    botAnswer.answer = Dialog.INSTANCE.getString("играем");
	    }
	    else if (input.startsWith("групповой"))
	    {    	
	    	botAnswer.answer = Dialog.INSTANCE.getString("группа");
	    	UserAboutGroupDialog chat = new UserAboutGroupDialog(this, groupRepository);
	    	botAnswer.buttons = Arrays.asList("главное меню :door:");
	    	fsm.setState(chat::check);
	    }
	    else if (input.startsWith("о себе")) {
			fsm.setState(this::modeSelection);
			botAnswer.buttons = Arrays.asList("одиночный :walking:",
                                              "групповой :family:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
	    else {
			fsm.setState(this::modeSelection);
			botAnswer.buttons = Arrays.asList("одиночный :walking:",
                                              "групповой :family:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
	   
		return botAnswer;
	}
	
	public BotAnswer gameSelection(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("виселица")) {
			fsm.setState(this::hangmanWordGeneration);
			currentGame = "виселица";
			botAnswer.buttons = Arrays.asList("ДА:fire:");
			botAnswer.answer = Dialog.INSTANCE.getString("начало");
		}
		else if (input.startsWith("правда или действие")) {
			fsm.setState(this::truthOrDareGetNames);
			currentGame = "правда или действие";
			botAnswer.buttons = Arrays.asList("ДА:fire:");
			botAnswer.answer = Dialog.INSTANCE.getString("начало");
		}
		else if (isAdmin && input.startsWith("редактировать")) {
			fsm.setState(adminDialog::whatGameEdit);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
            botAnswer.answer = Dialog.INSTANCE.getString("что редактировать");
		}
		else if (input.startsWith("главное меню")) {
			fsm.setState(this::modeSelection);		
			botAnswer.buttons = Arrays.asList("одиночный :walking:",
	                                          "групповой :family:",
	                                          "о себе :flushed:");                   
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else if (input.startsWith("о себе")) {
			fsm.setState(this::gameSelection);
			if (isAdmin) {
				botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                                  "виселица :detective:",
                                                  "редактировать :pencil2:",
                                                  "о себе :flushed:",
                                                  "главное меню :door:"); 
			}
			else {
				botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                                  "виселица :detective:",
                                                  "о себе :flushed:",
                                                  "главное меню :door:"); 
			}
			
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else {
			fsm.setState(this::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
					                          "виселица :detective:",
					                          "редактировать :pencil2:",
					                          "главное меню :door:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer hangmanWordGeneration(String input) {
		currentHangman = new Hangman(this, userRepository, chatId);
		BotAnswer botAnswer = new BotAnswer();
		fsm.setState(currentHangman::hangmanGame);
		botAnswer.buttons = Arrays.asList("о себе :flushed:",
				                          "статистика :heavy_check_mark:",
				                          "стоп :no_entry:");
		botAnswer.answer = currentHangman.setWord();
		return botAnswer;
	}
	
	public BotAnswer truthOrDareGetNames(String input) {
		fsm.setState(this::truthOrDareParseNames);
		BotAnswer botAnswer = new BotAnswer();
		botAnswer.buttons = Arrays.asList();
		botAnswer.answer = Dialog.INSTANCE.getString("игроки");
		return botAnswer;
	}
	
	public BotAnswer truthOrDareParseNames(String input) {
		currentTruthOrDare = new TruthOrDare(this);
		currentTruthOrDare.parseNames(input);
		fsm.setState(currentTruthOrDare::truthOrDareGame);
		BotAnswer botAnswer = new BotAnswer();
		botAnswer.buttons = Arrays.asList("правда :zipper_mouth:",
				                          "действие :tongue:",
				                          "о себе :flushed:",
				                          "стоп :no_entry:"); 
		botAnswer.answer = currentTruthOrDare.askPlayer();
		return botAnswer;
	}
	
	public BotAnswer reply(String input) {
		
		return fsm.update(input);
	}

}
