import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskMaker {

	private static ArrayList<String> words = new ArrayList<String>();

	public static void allWords() {
		try(BufferedReader br = new BufferedReader(new FileReader("src/Archive.txt")))
		{
			String word;
			while((word = br.readLine()) != null) {
				words.add(word);
			}
		}
		catch(IOException ex) {
			InOut.INSTANCE.output(ex.getMessage());
		}
	}
	
	public static String newTask() {
		if(words.size() == 0)
			allWords();
		Random rnd = new Random();
		int thisWord = rnd.nextInt(words.size());
		return words.get(thisWord);
	}
	
}
