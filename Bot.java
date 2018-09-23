import java.util.Scanner;

public class Bot {

	public static void main(String[] args) {
		
		Dialog curDialog = new Dialog();
		System.out.println(curDialog.sendStartMessage());
		Scanner in = new Scanner(System.in);
		
		while(true) {
			String request = in.nextLine();
			
			if (request.trim().toLowerCase().equals("о себе")) {
				System.out.println(curDialog.sendStartMessage());
				
			} else if (request.trim().endsWith("да")) {
				Hangman currentClient = new Hangman();
				currentClient.Game();
				if (currentClient.win == true) {
					System.out.println(curDialog.sendWinnerMessage());
				}
				else System.out.println(curDialog.sendLoserMessage());
				System.out.println(curDialog.sendRepeatedStartMessage());
				
			} else if (request.trim().endsWith("нет")) {
				System.out.println(curDialog.sendFarewellMessage());
				
			} else System.out.println(curDialog.sendStartMessage());
		}
		
	}
}
