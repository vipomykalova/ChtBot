package src.main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchiveEditor {
	
	private TaskMaker taskMaker = new TaskMaker();
	
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
			if (nameArchive.equals("Hangman")) {
				return Dialog.INSTANCE.getString("задание отсутствует") +
						getStrFromSimilarTasksList(getSimilarTasksHangman(nameArchive, task));
			}
			else {
				return Dialog.INSTANCE.getString("задание отсутствует") +
						getStrFromSimilarTasksList(getSimilarTasksTruthOrDare(nameArchive, task));
			}
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
	
	public List<String> getSimilarTasksHangman(String nameArchive, String task) {
		Map<String, Integer> taskOccurrenceStat = new HashMap<String, Integer>();
		List<String> similarTasks = new ArrayList<String>();
		for (String taskFromArchive: taskMaker.getListOfCurArchive(nameArchive)) {
			taskOccurrenceStat.put(taskFromArchive, 0);
		}
		for (int i = 0; i < task.length(); i++) {
			System.out.println(task.substring(i, i+1));
			for (String taskFromArchive: taskMaker.getListOfCurArchive(nameArchive)) {
				if (taskFromArchive.contains(task.substring(i, i+1))) {
					int count = taskOccurrenceStat.get(taskFromArchive);
					count += 1;
					taskOccurrenceStat.put(taskFromArchive, count);
				}
			}
		}
	
		taskOccurrenceStat.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach(v -> similarTasks.add(v.getKey()));
		
		List<String> result = new ArrayList<String>();
		
		for (int i = 0; i < task.length(); i++) {
			for (String tasks: similarTasks) {
				System.out.println(tasks);
				if (tasks.length() == (task.length() - i)) {
					result.add(tasks);
				}
			
			}
		}
		
		return result.subList(0, 4);
	}
	
	public List<String> getSimilarTasksTruthOrDare(String nameArchive, String task) {
		Map<String, Integer> taskOccurrenceStat = new HashMap<String, Integer>();
		List<String> similarTasks = new ArrayList<String>();
		for (String taskFromArchive: taskMaker.getListOfCurArchive(nameArchive)) {
			taskOccurrenceStat.put(taskFromArchive, 0);
		}
		for (int i = 0; i < task.length(); i++) {
			System.out.println(task.substring(i, i+1));
			for (String taskFromArchive: taskMaker.getListOfCurArchive(nameArchive)) {
				if (taskFromArchive.toLowerCase().contains(task.substring(0, i+1))) {
					int count = taskOccurrenceStat.get(taskFromArchive);
					count += 1;
					taskOccurrenceStat.put(taskFromArchive, count);
				}
			}
		}
		
		taskOccurrenceStat.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach(v -> similarTasks.add(v.getKey()));
		
		return similarTasks.subList(0, 4);
	}
	
	public String getStrFromSimilarTasksList(List<String> similarTasks) {
		String result = "Но может, что-то из этого подойдет:\n";
		for (String task: similarTasks) {
			result += task + "\n";
		}
		return result;
	}

}
