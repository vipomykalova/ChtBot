package src.main.java;

import java.util.ArrayList;
import com.vdurmont.emoji.EmojiParser;


public class MakerOfStatistics {
	
	public String getStatistics(ArrayList<ArrayList<Object>> topUsers) {	
		String result = "";
		for (ArrayList<Object> user: topUsers) {
			String name = user.get(0).toString();
			int wins = (int) user.get(1);
			int fails = (int) user.get(2);
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