import java.util.Scanner;

public class Bot {

	public static void main(String[] args) {
		
		System.out.println(Dialog.m_startMessage);
		Scanner in = new Scanner(System.in);
		
		while(true) {
			String request = in.nextLine();
			
			if (request.trim().toLowerCase().equals("î ñåáå")) {
				System.out.println(Dialog.m_startMessage);
			} else if (request.trim().equals("")) {
				System.out.println(Dialog.sendTalkToMe());
			} else if (request.trim().endsWith("?")) {
				System.out.println(Dialog.sendAnswer());
			} else System.out.println(Dialog.sendPhraseAndQuestion());
		}
		
	}
}
