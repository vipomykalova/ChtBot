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
		archives.put("Hangman", makeTaskList(new ArrayList<String>(), "src/Archives/ArchiveHangman.txt"));
		archives.put("Dare", makeTaskList(new ArrayList<String>(), "src/Archives/ArchiveDare.txt"));
		archives.put("Truth", makeTaskList(new ArrayList<String>(), "src/Archives/ArchiveTruth.txt"));
		
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
		archives.computeIfAbsent(curArchive, k -> makeTaskList(new ArrayList<String>(),
				             "src/Archives/Archive" + curArchive + ".txt"));
		int thisTask = rnd.nextInt(archives.get(curArchive).size());
		return archives.get(curArchive).get(thisTask);
	}

}