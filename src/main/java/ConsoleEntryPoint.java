package src.main.java;

public class ConsoleEntryPoint {

	public static void main(String[] args) {
		InOutConsole console = new InOutConsole();

		while(true) {
			try {
				String userInput = console.input();
				String[] input = userInput.split(":");
				String response = UserRepository.users.computeIfAbsent(Long.parseLong(input[0]),
						          k -> new Brain()).reply(input[1]).answer;
				UserRepository.users.get(Long.parseLong(input[0])).username = input[0];
				console.output(response);
			}
			catch (ArrayIndexOutOfBoundsException exp){
				console.output(Dialog.INSTANCE.getString("пример ввода"));
			}
		}
	}
}
