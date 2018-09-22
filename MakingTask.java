import java.io.*;
import java.util.Random;

public class MakingTask {
	
	public static String newTask() {
		
		Random rnd = new Random();
		int thisWord = rnd.nextInt(2);
		try(BufferedReader br = new BufferedReader(new FileReader("Archive.txt")))
		{
		    String s;
		    int count = 0;
		    while((s = br.readLine()) != null) {
		    	if(count == thisWord)
		    		return s;
		        count ++;
		    }
		}
		catch(IOException ex) {
			return null;
		}
		return null; 
	}

}
