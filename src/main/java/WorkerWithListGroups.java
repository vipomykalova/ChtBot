package src.main.java;

import java.util.ArrayList;

public class WorkerWithListGroups {
		
	public void addWord(GroupsBrain groupsBrain, String word)
	{
		groupsBrain.words.add(word);
	}
	
	public String getWord(ArrayList<String> words)
	{
		String word = words.get(0);
		words.remove(0);
		return word;
	}
}
