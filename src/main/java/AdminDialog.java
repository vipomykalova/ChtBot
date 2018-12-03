package src.main.java;

import java.util.Arrays;


public class AdminDialog {
	
	private Brain currentUser;
	private ArchiveEditor archiveEditor = new ArchiveEditor();
	
	public AdminDialog(Brain brain) {
		currentUser = brain;
	}
	
	public BotAnswer whatGameEdit(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("виселица")) {
			currentUser.fsm.setState(this::hangmanEdit);
			botAnswer.buttons = Arrays.asList("удалить:x:",
                                              "добавить:heavy_check_mark:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
            botAnswer.answer = Dialog.INSTANCE.getString("виселица");
		}
		else if (input.startsWith("правда или действие")) {
			currentUser.fsm.setState(this::truthOrDareEdit);
			botAnswer.buttons = Arrays.asList("удалить:x:",
                                              "добавить:heavy_check_mark:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
            botAnswer.answer = Dialog.INSTANCE.getString("правда или действие");
		}
		else if (input.startsWith("выход")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::whatGameEdit);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::whatGameEdit);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer truthOrDareEdit(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("удалить")) {
			currentUser.fsm.setState(this::truthOrDareRemove);
			botAnswer.buttons = Arrays.asList("правду :zipper_mouth:",
                                              "действие :tongue:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("что удалить");
		}
		else if (input.startsWith("добавить")) {
			currentUser.fsm.setState(this::truthOrDareAdd);
			botAnswer.buttons = Arrays.asList("правду :zipper_mouth:",
                                              "действие :tongue:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
            botAnswer.answer = Dialog.INSTANCE.getString("что добавить");
		}
		else if (input.startsWith("выход")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::truthOrDareEdit);
			botAnswer.buttons = Arrays.asList("удалить:x:",
                                              "добавить:heavy_check_mark:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::truthOrDareEdit);
			botAnswer.buttons = Arrays.asList("удалить:x:",
                                              "добавить:heavy_check_mark:",
                                              "выход :door:",
                                              "о себе :flushed:");  
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer hangmanEdit(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("удалить")) {
			currentUser.fsm.setState(this::hangmanRemove);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("удалить");
		}
		else if (input.startsWith("добавить")) {
			currentUser.fsm.setState(this::hangmanAdd);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("добавить");
		}
		else if (input.startsWith("выход")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::hangmanEdit);
			botAnswer.buttons = Arrays.asList("удалить:x:",
                                              "добавить:heavy_check_mark:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::hangmanEdit);
			botAnswer.buttons = Arrays.asList("удалить:x:",
                                              "добавить:heavy_check_mark:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer truthOrDareAdd(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("правду")) {
			currentUser.fsm.setState(this::truthAdd);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("добавить");
		}
		else if (input.startsWith("действие")) {
			currentUser.fsm.setState(this::dareAdd);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("добавить");
		}
		else if (input.startsWith("выход")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::truthOrDareAdd);
			botAnswer.buttons = Arrays.asList("правду :zipper_mouth:",
                                              "действие :tongue:",
                                              "выход :door:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::truthOrDareAdd);
			botAnswer.buttons = Arrays.asList("правду :zipper_mouth:",
                                              "действие :tongue:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer truthAdd(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = archiveEditor.addToArchive("Truth", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                          "выход :door:",
                                          "о себе :no_entry:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer dareAdd(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = archiveEditor.addToArchive("Dare", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                          "выход :door:",
                                          "о себе :no_entry:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer truthOrDareRemove(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("правду")) {
			currentUser.fsm.setState(this::truthRemove);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("удалить");
		}
		else if (input.startsWith("действие")) {
			currentUser.fsm.setState(this::dareRemove);
			botAnswer.buttons = Arrays.asList();
			botAnswer.answer = Dialog.INSTANCE.getString("удалить");
		}
		else if (input.startsWith("выход")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::truthOrDareRemove);
			botAnswer.buttons = Arrays.asList("правду :zipper_mouth:",
                                              "действие :tongue:",
                                              "выход :door:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::truthOrDareRemove);
			botAnswer.buttons = Arrays.asList("правду :zipper_mouth:",
                                              "действие :tongue:",
                                              "выход :door:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
	public BotAnswer truthRemove(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = archiveEditor.removeFromArchive("Truth", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                          "выход :door:",
                                          "о себе :flushed:");
		botAnswer.answer = result;
		return botAnswer;
		
	}
	
	public BotAnswer dareRemove(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = archiveEditor.removeFromArchive("Dare", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                          "выход :door:",
                                          "о себе :flushed:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer hangmanRemove(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = archiveEditor.removeFromArchive("Hangman", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                          "выход :door:",
                                          "о себе :flushed:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer hangmanAdd(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = archiveEditor.addToArchive("Hangman", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                          "выход :door:",
                                          "о себе :flushed:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer editMore(String input) {
		BotAnswer botAnswer = new BotAnswer();
		if (input.startsWith("ещё")) {
			currentUser.fsm.setState(this::whatGameEdit);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "выход :door:",
                                              "о себе :flushed:"); 
            botAnswer.answer = Dialog.INSTANCE.getString("что редактировать");
		}
		else if (input.startsWith("выход")) {
			currentUser.fsm.setState(currentUser::gameSelection);
			botAnswer.buttons = Arrays.asList("правда или действие :underage:",
                                              "виселица :detective:",
                                              "редактировать :pencil2:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("приветствие");			
		}
		else if (input.startsWith("о себе")) {
			currentUser.fsm.setState(this::editMore);
			botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                              "выход :door:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::editMore);
			botAnswer.buttons = Arrays.asList("ещё :relieved:",
                                              "выход :door:",
                                              "о себе :flushed:");
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}

}
