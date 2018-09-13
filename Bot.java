import java.util.Scanner;

public class Bot {

	public static void main(String[] args) {
		
		Dialog.printStartMessage();
		
		while(true) {
			Scanner in = new Scanner(System.in);
			String request = in.nextLine();
			
			Dialog.answers(request);
		}


	}

}
