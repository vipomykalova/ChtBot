package src.main.java;
import java.util.Collections;
import java.util.List;

import com.vdurmont.emoji.EmojiParser;

public class MakerOfStatistics {
	
	public static String getStatistics() {	
		String result = "";
		List<Brain> statistics = (List<Brain>) UserRepository.users.values();
		Collections.sort(statistics, Collections.reverseOrder());
		for (Brain user: statistics) {
			if (user.wins != 0 || user.fails != 0)
			{
				result = result + user.username + " :heavy_plus_sign::" +
			             user.wins + " :heavy_minus_sign::" +
						 user.fails + "\n";
			}
		}
		return result.equals("") ? "Никто еще не играл, увы.." : EmojiParser.parseToUnicode(result);
	}

}
