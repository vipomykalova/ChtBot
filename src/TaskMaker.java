import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskMaker {

	public static ArrayList<String> allTasks(String curArchive) {
		
		ArrayList<String> tasks = new ArrayList<String>();
		String archive = "";
		
		if (curArchive.equals("виселица")) {
			archive = "src/ArchiveHangman.txt"; 
		}
		if (curArchive.equals("правда")) {
			archive = "src/ArchiveTruth.txt";
		}
		if (curArchive.equals("действие")) {
			archive = "src/ArchiveDare.txt";
		}

		try(BufferedReader br = new BufferedReader(new FileReader(archive)))
		{
			String task;
			while((task = br.readLine()) != null) {
				tasks.add(task);
			}
		}
		catch(IOException ex) {
			InOut.INSTANCE.output(ex.getMessage());
		}
		
		return tasks;
	}
	
	public static String newTask(String curArchive) {
		ArrayList<String> tasks = allTasks(curArchive);
		Random rnd = new Random();
		int thisTask = rnd.nextInt(tasks.size());
		return tasks.get(thisTask);
	}
	
}
