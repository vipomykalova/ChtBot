import java.util.Scanner;

public class Brain implements InputOutput{
	
	FMS brain = new FMS();
	String currentAnswer = "";
	Dialog curDialog = new Dialog();
	
	Brain() {
		brain.setState(() -> LetsPlay());
	}
	
	public String Input() {
		Scanner in = new Scanner(System.in);
		return in.nextLine().trim();
	}
	
	public void Output(String keyWord) {
		System.out.println(curDialog.getString(keyWord));
	}
	
	public void LetsPlay() {	
		System.out.println(curDialog.getString("привет"));
		currentAnswer = Input();
		if (currentAnswer.equals("нет")) brain.setState(() -> WaitingForYou());
		if (currentAnswer.equals("да")) brain.setState(() -> Word());
		if (currentAnswer.equals("о себе")) brain.setState(() -> LetsPlay());
	}
	
	public void Word() {
		Hangman currentClient = new Hangman();
		currentClient.Game();
		
		if (currentClient.win == true) {
			Output("победа");
			brain.setState(() -> DoYouWant());
		}
		if (currentClient.win == false) {
			Output("проигрыш");
			brain.setState(() -> DoYouWant());
		}
	}
	
	public void DoYouWant() {
		Output("еще");
		currentAnswer = Input();
		if (currentAnswer.equals("да")) brain.setState(() -> Word());
		if (currentAnswer.equals("нет")) brain.setState(() -> WaitingForYou());
		if (currentAnswer.equals("о себе")) brain.setState(() -> LetsPlay());
	}
	
	public void WaitSmth() {
		currentAnswer = Input();
		if (currentAnswer.equals("привет") || currentAnswer.equals("о себе")) brain.setState(() -> LetsPlay());
	}
	
	public void WaitingForYou() {
		Output("пока");
		brain.setState(() -> WaitSmth());
	}
	
	public void Update() {
		brain.update();
	}

}
