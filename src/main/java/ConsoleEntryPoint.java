package src.main.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleEntryPoint {
	
	public static Map<String, UsersBrain> users = new ConcurrentHashMap<String, UsersBrain>();
	
	public static void main(String[] args) {
		InOutConsole console = new InOutConsole();

		while(true) {
			try {
				String userInput = console.input();
				String[] input = userInput.split(":");
				String response = users.computeIfAbsent(input[0],
						          k -> new UsersBrain()).reply(input[1]).answer;
				console.output(response);
			}
			catch (ArrayIndexOutOfBoundsException exp){
				console.output(Dialog.INSTANCE.getString("пример ввода"));
			}
		}
	}
}
