package src.main.java;

import java.util.ArrayList;
import com.vdurmont.emoji.EmojiParser;


public class MakerOfStatistics {
	
	public String getStatistics(ArrayList<Brain> topUsers) {	
		String result = "";
		for (Brain user: topUsers) {
			if (user.wins != 0 || user.fails != 0)
			{
				result = result + user.username + " :heavy_plus_sign::" +
			             user.wins + " :heavy_minus_sign::" +
						 user.fails + "\n";
			}
		}
		return result.equals("") ? "Никто еще не играл, увы..\n" : EmojiParser.parseToUnicode(result);
	}
}