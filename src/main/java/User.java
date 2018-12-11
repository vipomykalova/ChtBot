package src.main.java;

public class User {
	public String username;
	public int wins;
	public int fails;
	
	public User()
	{
		username = "";
		wins = 0;
		fails = 0;
	}
	public User(String username)
	{
		this.username = username;
		wins = 0;
		fails = 0;
	}
}