import java.util.Scanner;

public class Bot {

	public static void main(String[] args) {
		
		System.out.println("ѕривет. я твой новый чат бот. “ы ожешь немного поговорить"
				+ "со мной или снова увидеть это вообщение написав \"–асскажи о себе\"");
		
		while(true) {
			Scanner in = new Scanner(System.in);
			String request = in.nextLine();
			
			Library.answers(request);
		}


	}

}
