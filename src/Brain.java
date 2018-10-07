
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
		    case "виселица":
		        brain.setState(this::hangman);
		        break;
		    case "правда или действие":
		        brain.setState(this::truthOrDare);
		        break;
		    case "о себе":
		        brain.setState(this::letsPlay);
		        break;
		}
	}
	
	public void truthOrDare() {
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("правда или действие"));
		currentAnswer = InOut.INSTANCE.input();
		switch(currentAnswer) {
		    case "да":
			    brain.setState(this::truthOrDareGame);
			    break;
		    case "нет":
			    brain.setState(this::sendByeMessage);
			    break;
		    case "о себе":
		    	brain.setState(this::letsPlay);
		    	break;
		}
	}
	
	public void truthOrDareGame() {
		TruthOrDare currentClient = new TruthOrDare();
		currentClient.game();
		
		if (currentClient.endOfGame) {
			InOut.INSTANCE.output(Dialog.INSTANCE.getString("конец"));}
		
		brain.setState(this::sendByeMessage);
	}
	
	public void hangman() {
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("виселица"));
		currentAnswer = InOut.INSTANCE.input();
		switch(currentAnswer) {
		    case "да":
			    brain.setState(this::hangmanGame);
			    break;
		    case "нет":
			    brain.setState(this::sendByeMessage);
			    break;
		    case "о себе":
			    brain.setState(this::letsPlay);
			    break;
		}
	}

	public void hangmanGame() {
		Hangman currentClient = new Hangman();
		currentClient.game();

		if (currentClient.win) {
			InOut.INSTANCE.output(Dialog.INSTANCE.getString("победа"));
		}
		else {
			InOut.INSTANCE.output(Dialog.INSTANCE.getString("проигрыш"));
		}
		brain.setState(this::doYouWantInHangman);
	}
	
	public void doYouWantInHangman() {
		InOut.INSTANCE.output(Dialog.INSTANCE.getString("еще"));
		currentAnswer = InOut.INSTANCE.input();
		switch(currentAnswer) {
			case "нет":
				brain.setState(this::sendByeMessage);
				break;
			case "да":
				brain.setState(this::hangmanGame);
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
