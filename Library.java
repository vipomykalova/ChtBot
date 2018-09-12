
public class Library {
	
	public static void answers(String request) {
		
		if (request.equals("Расскажи о себе") || request.equals("расскажи о себе")) {
			System.out.println("Привет. Я твой новый чат бот. Пока что ничего не умею");
		} else if(request.equals("Привет") || request.equals("привет")){
			System.out.println("Рад тебя снова видеть");
		}
		
	}

}
