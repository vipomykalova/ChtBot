import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskMaker {

	private static ArrayList<String> tasks = new ArrayList<String>();
	private static String currentArchive;

	public static void allTasks(String curArchive) {

		String archive = "src/Archive" + curArchive + ".txt";
		tasks.clear();

		try(BufferedReader br = new BufferedReader(new FileReader(archive)))
		{
			String task;
			while((task = br.readLine()) != null) {
				tasks.add(task);
			}
		}
		catch(IOException ex) {
			System.exit(1);
		}
	}

	public static String newTask(String curArchive) {
		if(!curArchive.equals(currentArchive)) {
			allTasks(curArchive);
			currentArchive = curArchive;
		}
		Random rnd = new Random();
		int thisTask = rnd.nextInt(tasks.size());
		return tasks.get(thisTask);
	}

}