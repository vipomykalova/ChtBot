package src.main.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleEntryPoint {
	
	public static Map<String, Brain> users = new ConcurrentHashMap<String, Brain>();
	
	public static void main(String[] args) {
		InOutConsole console = new InOutConsole();

		while(true) {
			try {
				String userInput = console.input();
				String[] input = userInput.split(":");
				String response = users.computeIfAbsent(input[0],
						          k -> new Brain()).reply(input[1]).answer;
				console.output(response);
			}
			catch (ArrayIndexOutOfBoundsException exp){
				console.output(Dialog.INSTANCE.getString("пример ввода"));
			}
		}
	}
}
