package src.main.java;

import java.io.IOException;
import java.util.ArrayList;


public interface Database {
	
	public ArrayList<Object> getOrCreate(Long chatId);
	public ArrayList<ArrayList<Object>> getTopUsers();
	public void initDatabase() throws IOException;
	public void saveInDatabase(Long chatId, ArrayList<Object> data);

}