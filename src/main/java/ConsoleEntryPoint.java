package main.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleEntryPoint {

	public static void main(String[] args) {
		Map<String, Brain> players = new ConcurrentHashMap<String, Brain>();
		InOutConsole console = new InOutConsole();

		while(true) {
			try {
				String userInput = console.input();
				String[] input = userInput.split(":");
				String response = players.computeIfAbsent(input[0], k -> new Brain()).reply(input[1]).answer;
				console.output(response);
			}
			catch (ArrayIndexOutOfBoundsException exp){
				console.output(Dialog.INSTANCE.getString("пример ввода"));
			}
		}
	}
}
