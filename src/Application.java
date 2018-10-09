
public class Application {

	public static void main(String[] args) {
		Brain brain = new Brain();

		while(true) {
			String userInput = InOut.INSTANCE.input();
			String response = brain.update(userInput);
			InOut.INSTANCE.output(response);
		}
	}
}
