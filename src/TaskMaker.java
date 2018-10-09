import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskMaker {

	public static ArrayList<String> allTasks(String curArchive) {
		
		InOutConsole console = new InOutConsole();
		
		ArrayList<String> tasks = new ArrayList<String>();
		
		String archive = "src/Archive" + curArchive + ".txt";

		try(BufferedReader br = new BufferedReader(new FileReader(archive)))
		{
			String task;
			while((task = br.readLine()) != null) {
				tasks.add(task);
			}
		}
		catch(IOException ex) {
			console.output(ex.getMessage());
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
