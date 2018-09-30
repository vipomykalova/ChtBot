
public class Brain extends  InputOutput{
	
	FMS brain = new FMS();
	String currentAnswer = "";
	Dialog curDialog = new Dialog();
	
	Brain() {
		brain.setState(() -> letsPlay());
	}
	
	public void letsPlay() {	
		output(curDialog.getString("привет"));
		currentAnswer = input();
		if (currentAnswer.equals("нет")) brain.setState(() -> sendByeMessage());
		if (currentAnswer.equals("да")) brain.setState(() -> getWord());
		if (currentAnswer.equals("о себе")) brain.setState(() -> letsPlay());
	}
	
	public void getWord() {
		Hangman currentClient = new Hangman();
		currentClient.game();
		
		if (currentClient.win == true) {
			output(curDialog.getString("победа"));
			brain.setState(() -> doYouWant());
		}
		if (currentClient.win == false) {
			output(curDialog.getString("проигрыш"));
			brain.setState(() -> doYouWant());
		}
	}
	
	public void doYouWant() {
		output(curDialog.getString("еще"));
		currentAnswer = input();
		if (currentAnswer.equals("да")) brain.setState(() -> getWord());
		if (currentAnswer.equals("нет")) brain.setState(() -> sendByeMessage());
		if (currentAnswer.equals("о себе")) brain.setState(() -> letsPlay());
	}
	
	public void waitSmth() {
		currentAnswer = input();
		if (currentAnswer.equals("привет") || currentAnswer.equals("о себе")) brain.setState(() -> letsPlay());
	}
	
	public void sendByeMessage() {
		output(curDialog.getString("пока"));
		brain.setState(() -> waitSmth());
	}
	
	public void update() {
		brain.update();
	}

}
