
public class Brain {
	
	FMS brain = new FMS();
	String currentAnswer = "";
	
	Brain() {
		brain.setState(this::letsPlay);
	}
	
	public void letsPlay() {
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("привет"));
		currentAnswer = InOut.INSTANCE.input();
		switch(currentAnswer) {
			case "нет":
				brain.setState(this::sendByeMessage);
				break;
			case "да":
				brain.setState(this::getWord);
				break;
			case "о себе":
				brain.setState(this::letsPlay);
				break;
		}
	}

	public void getWord() {
		Hangman currentClient = new Hangman();
		currentClient.game();

		if (currentClient.win) {
			InOut.INSTANCE.output(Dialog.INSTANCE.getString("победа"));
		}
		if (!currentClient.win) {
			InOut.INSTANCE.output(Dialog.INSTANCE.getString("проигрыш"));
		}
		brain.setState(this::doYouWant);
	}
	
	public void doYouWant() {
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("еще"));
		currentAnswer = InOut.INSTANCE.input();
		switch(currentAnswer) {
			case "нет":
				brain.setState(this::sendByeMessage);
				break;
			case "да":
				brain.setState(this::getWord);
				break;
			case "о себе":
				brain.setState(this::letsPlay);
				break;
		}
	}
	
	public void waitSmth() {
		currentAnswer = InOut.INSTANCE.input();
		if (!currentAnswer.isEmpty()) {
			brain.setState(this::letsPlay);
		}
		else throw new IllegalStateException();
	}
	
	public void sendByeMessage() {
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("пока"));
		brain.setState(this::waitSmth);
	}
	
	public void update() {
		brain.update();
	}

}
