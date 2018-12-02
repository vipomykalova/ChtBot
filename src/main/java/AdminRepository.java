package src.main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Paths;


public class AdminRepository {
	
	private TaskMaker taskMaker = new TaskMaker();
	private Brain currentUser;
	
	public AdminRepository(Brain brain) {
		currentUser = brain;
	}
	
	public Boolean checkAdmin(Long id) {
		String[] listOfAdmins = System.getenv("ADMINS").split(":");
		for (int i = 0; i < listOfAdmins.length; i++) {
			if (listOfAdmins[i].equals(id.toString())) {
				return true;				
			}
		}
		return false;
	}
	
	public String addToArchive(String nameArchive, String task) {
		
		String filename = "src/main/java/Archives/Archive" + nameArchive + ".txt";
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			String line;
			while((line = br.readLine()) != null) {
				if (task.equals(line)) {
					return Dialog.INSTANCE.getString("задание уже есть");
				}
			}
			Files.write(Paths.get(filename), ("\n" + task).getBytes(), StandardOpenOption.APPEND);
			taskMaker.addToListTask(nameArchive, task);
			return Dialog.INSTANCE.getString("добавлено");
		}
		catch(IOException ex) {
			System.exit(1);
		}
		return Dialog.INSTANCE.getString("ошибка");
		
	}
	
	public String removeFromArchive(String nameArchive, String task) {
		
		String filename = "src/main/java/Archives/Archive" + nameArchive + ".txt";
		ArrayList<String> fileContents = new ArrayList<String>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			String line;
			while((line = br.readLine()) != null) {
				if (!task.equals(line)) {
					fileContents.add(line);
				}
			}
		}
		catch(IOException ex) {
			System.exit(1);
		}
		if (fileContents.size() == taskMaker.getSizeListTask(nameArchive)) {
			return Dialog.INSTANCE.getString("задание отсутствует");
		}
		File fold = new File(filename);
		fold.delete();
		File fnew = new File(filename);
		
		try {
			PrintWriter file = new PrintWriter(new FileWriter(fnew));
            for (int line = 0; line < fileContents.size(); line++) {
            	if (line != fileContents.size()- 1)
            		file.println(fileContents.get(line));
            	else
            	   file.print(fileContents.get(line));
            }                     
            file.close();
            taskMaker.removeFromListTask(nameArchive, task);
            return Dialog.INSTANCE.getString("удалено");
            
		} catch (IOException e) {
			System.exit(1);
        }
		return Dialog.INSTANCE.getString("ошибка");
		
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
		String result = addToArchive("Truth", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :x:",
                                          "выход :door:",
                                          "о себе :no_entry:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer dareAdd(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = addToArchive("Dare", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :x:",
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
		String result = removeFromArchive("Truth", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :x:",
                                          "выход :door:",
                                          "о себе :no_entry:");
		botAnswer.answer = result;
		return botAnswer;
		
	}
	
	public BotAnswer dareRemove(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = removeFromArchive("Dare", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :x:",
                                          "выход :door:",
                                          "о себе :no_entry:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer hangmanRemove(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = removeFromArchive("Hangman", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :x:",
                                          "выход :door:",
                                          "о себе :no_entry:");
		botAnswer.answer = result;
		return botAnswer;
	}
	
	public BotAnswer hangmanAdd(String input) {
		BotAnswer botAnswer = new BotAnswer();
		String result = addToArchive("Hangman", input);
		currentUser.fsm.setState(this::editMore);
		botAnswer.buttons = Arrays.asList("ещё :x:",
                                          "выход :door:",
                                          "о себе :no_entry:");
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
			botAnswer.buttons = Arrays.asList("ещё :x:",
                                              "выход :door:",
                                              "о себе :no_entry:");
			botAnswer.answer = Dialog.INSTANCE.getString("расскажи");
		}
		else {
			currentUser.fsm.setState(this::editMore);
			botAnswer.buttons = Arrays.asList("ещё :x:",
                                              "выход :door:",
                                              "о себе :no_entry:");
			botAnswer.answer = Dialog.INSTANCE.getString("некорректный ввод");
		}
		return botAnswer;
	}
	
}
