package ChatBot;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Map;

public class TaskMaker {
	
	private Map<String, ArrayList<String>> archives;
	
	public TaskMaker() {
		archives = new ConcurrentHashMap<String, ArrayList<String>>();
		archives.put("Hangman", makeListTasks(new ArrayList<String>(), "src/Archives/ArchiveHangman.txt"));
		archives.put("Dare", makeListTasks(new ArrayList<String>(), "src/Archives/ArchiveDare.txt"));
		archives.put("Truth", makeListTasks(new ArrayList<String>(), "src/Archives/ArchiveTruth.txt"));
		
	}
	
	private ArrayList<String> makeListTasks (ArrayList<String> listArchive, String nameArchive) {
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
		archives.putIfAbsent(curArchive, makeListTasks(new ArrayList<String>(),
				             "src/Archives/Archive" + curArchive + ".txt"));
		int thisTask = rnd.nextInt(archives.get(curArchive).size());
		return archives.get(curArchive).get(thisTask);
	}

}