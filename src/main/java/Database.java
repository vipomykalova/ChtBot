package src.main.java;

import java.io.IOException;


public interface Database {
	
	public void getOrCreate(Long chatId);
	public void getTopUsers();
	public void initDatabase() throws IOException;
	public void saveInDatabase(Long chatId);

}