package src.main.java;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Map;

public class TaskMaker {
	
	private static Map<String, ArrayList<String>> tasks;
	
	public TaskMaker() {
		tasks = new ConcurrentHashMap<String, ArrayList<String>>();
		tasks.put("Hangman", makeTaskList(new ArrayList<String>(), "src/main/java/Archives/ArchiveHangman.txt"));
		tasks.put("Dare", makeTaskList(new ArrayList<String>(), "src/main/java/Archives/ArchiveDare.txt"));
		tasks.put("Truth", makeTaskList(new ArrayList<String>(), "src/main/java/Archives/ArchiveTruth.txt"));
		
	}
	
	private ArrayList<String> makeTaskList (ArrayList<String> listArchive, String nameArchive) {
		try(BufferedReader br = new BufferedReader(new FileReader(nameArchive)))
		{
			String task;
			while((task = br.readLine()) != null) {
				listArchive.add(task);
			}
		}
		catch(IOException ex) {
			System.exit(1);
		}
		
		return listArchive;
	}

	public String newTask(String curArchive) {
		Random rnd = new Random();
		tasks.computeIfAbsent(curArchive, k -> makeTaskList(new ArrayList<String>(),
				             "src/main/java/Archives/Archive" + curArchive + ".txt"));
		int thisTask = rnd.nextInt(tasks.get(curArchive).size());
		return tasks.get(curArchive).get(thisTask);
	}
	
	public void addToListTask(String nameArchive, String task) {
		tasks.get(nameArchive).add(task);
	}
	
	public void removeFromListTask(String nameArchive, String task) {
		tasks.get(nameArchive).remove(TaskMaker.tasks.get(nameArchive).indexOf(task));
	}
	
	public int getSizeListTask(String nameArchive) {
		return tasks.get(nameArchive).size();
	}
	
	public ArrayList<String> getListOfCurArchive(String nameArchive) {
		return tasks.get(nameArchive);
	}

}