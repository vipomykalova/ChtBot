import java.io.*;
import java.util.Random;

public class MakingTask {
	
	public Map<int,String> wordsDict() {
		Map<int,String> dictionary = new HashMap<int,String>();
		
		try(BufferedReader br = new BufferedReader(new FileReader("Archive.txt")))
		{
		    String s;
		    int count = 0;
		    while((s = br.readLine()) != null) {
		    	dictionary.put(count, s);
		        count ++;
		    }
		}
		catch(IOException ex) {
			System.out.println(ex.getMessage());;
		}
	}
	
	public static String newTask() {
		Map<int,String> dictionary = wordsDict();
		Random rnd = new Random();
		int thisWord = rnd.nextInt(dictionary.size());
		return dictionary.get(thisWord);
	}
	
}
