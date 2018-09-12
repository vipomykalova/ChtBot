import java.util.Scanner;

public class Bot {

	public static void main(String[] args) {
		
		System.out.println("Привет. Я твой новый чат бот. Ты ожешь немного поговорить"
				+ "со мной или снова увидеть это вообщение написав \"Расскажи о себе\"");
		
		while(true) {
			Scanner in = new Scanner(System.in);
			String request = in.nextLine();
			
			Library.answers(request);
		}


	}

}
