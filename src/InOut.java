import java.util.Scanner;

public class InOut {
	
	public static final InOut INSTANCE = new InOut();
	
	public String input() {
		Scanner in = new Scanner(System.in);
		return in.nextLine().trim();
	}
	
	public void output(String word) {
		System.out.print(word);
	}

}
