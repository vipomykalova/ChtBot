package src.main.java;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.vdurmont.emoji.EmojiParser;

public class MakerOfStatistics {
	
	public static String generateUserStatistic(Statistics currentStatistics) {
		
		String name = TelegramEntryPoint.usernames.get(currentStatistics.currentUser);
		
		String result = name + " :heavy_plus_sign::" + currentStatistics.wins +
				" :heavy_minus_sign::" + currentStatistics.fails;
		
		return EmojiParser.parseToUnicode(result);
	}

	
	public static String getStatistics() {	
		String result = "";
		for (Brain user: UserRepository.users.values().stream().sorted(Comparator.comparing(Statistics::wins).reversed())) {
			
		}
		else {
			Map<Brain, Integer> usersWinsSortedMap = usersWins.entrySet().stream()
		        .sorted(Map.Entry.<Brain, Integer>comparingByValue().reversed())
		        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			for (Map.Entry<Brain, Integer> entry: usersWinsSortedMap.entrySet()) {
				result = result + usersStatistics.get(entry.getKey()) + "\n";
			}
		}
		return EmojiParser.parseToUnicode(result);
	}

}
