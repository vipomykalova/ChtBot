
public class Library {
	
	public static void answers(String request) {
		
		if (request.equals("Расскажи о себе") || request.equals("расскажи о себе")) {
			System.out.println("Привет. Я твой новый чат бот. Ты ожешь немного поговорить"
					+ "со мной или снова увидеть это вообщение написав \"Расскажи о себе\"");
		} else if(request.equals("Привет") || request.equals("привет")){
			System.out.println("Рад тебя снова видеть");
		}
		
	}

}
