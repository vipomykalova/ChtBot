package ChatBot;

public class Application {

	public static void main(String[] args) {
		Brain brain = new Brain();
		InOutConsole console = new InOutConsole();

		while(true) {
			String userInput = console.input();
			String response = brain.reply(userInput);
			console.output(response);
		}
	}
}
