import java.util.Scanner;

public class Bot {

	public static void main(String[] args) {
		
		Library.printStartMessage();
		
		while(true) {
			Scanner in = new Scanner(System.in);
			String request = in.nextLine();
			
			Library.answers(request);
		}


	}

}
