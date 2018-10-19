package ChatBot;

import java.util.HashMap;
import java.util.Map;

public class Application {

	public static void main(String[] args) {
		Map<String, Brain> players = new HashMap<String, Brain>();
		//Map<String, String> games = new HashMap<String, String>();
		//Brain brain = new Brain();
		InOutConsole console = new InOutConsole();

		while(true) {
			String userInput = console.input();
			String[] input = userInput.split(":");
			players.putIfAbsent(input[0], new Brain());
			String response = players.get(input[0]).reply(input[1]);
			console.output(response);
		}
	}
}
