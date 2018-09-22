
public class Dialog {

	
	private final String START_MESSAGE = "Привет :) Я бот, который умеет "
			+ "играть в \"Виселицу\". Поиграем? Напиши \"да\" или \"нет\".";
	
	private final String WINNER_MESSAGE = "Поздравляю, ты победил!";
	
	private final String LOSER_MESSAGE = "Упс, ты проиграл :(";
	
	private final String REPEATED_MESSAGE = "Сыграем еще?";
	
	private final String FAREWELL_MESSAGE = "Обидно! Но если вдруг захочешь,"
			+ " то пиши мне :) Я буду ждать!";
	
	public String sendStartMessage() {
		return START_MESSAGE;
	}
	
	public String sendWinnerMessage() {
		return  WINNER_MESSAGE;
	}
	
	public String sendLoserMessage() {
		return  LOSER_MESSAGE;
	}
	
	public String sendRepeatedStartMessage() {
		return REPEATED_MESSAGE;
	}
	
	public String sendFarewellMessage() {
		return FAREWELL_MESSAGE;
	}
	
	
}
