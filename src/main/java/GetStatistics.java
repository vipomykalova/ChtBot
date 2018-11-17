package main.java;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.vdurmont.emoji.EmojiParser;

public class GetStatistics {
	
	public static Map<Brain, String> usersStatistics = new HashMap<Brain, String>();
	public static Map<Brain, Integer> usersWins = new HashMap<Brain, Integer>();
	
	public static String generateUserStatistic(Statistics currentStatistics) {
		
		String name = TelegramEntryPoint.usernames.get(currentStatistics.currentUser);
		
		String result = name + " :white_check_mark::" + currentStatistics.wins +
				" :negative_squared_cross_mark::" + currentStatistics.fails;
		
		return EmojiParser.parseToUnicode(result);
	}
	
	public static void refreshUserStatistics(Statistics currentStatistics) {
		String userStatisticsInString = generateUserStatistic(currentStatistics);
		usersStatistics.put(currentStatistics.currentUser, userStatisticsInString);
		usersWins.put(currentStatistics.currentUser, currentStatistics.wins);
	}
	
	public static String getStatistics() {	
		String result = "";
		if (usersStatistics.isEmpty()) {
			result = "Никто еще не опробовал нашу супер игру :disappointed:";
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
