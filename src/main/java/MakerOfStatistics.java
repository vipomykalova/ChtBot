package src.main.java;

import java.util.ArrayList;
import com.vdurmont.emoji.EmojiParser;


public class MakerOfStatistics {
	
	public String getStatistics(ArrayList<User> topUsers) {	
		String result = "";
		for (User user: topUsers) {
			String name = user.username;
			int wins = user.wins;
			int fails = user.fails;
			if (wins != 0 || fails != 0)
			{
				result = result + name + " :heavy_plus_sign::" +
			             wins + " :heavy_minus_sign::" +
						 fails + "\n";
			}
		}
		return result.equals("") ? "Никто еще не играл, увы..\n" : EmojiParser.parseToUnicode(result);
	}
}