import java.util.Random;

public class Dialog {
	
	private static Random random = new Random();
	
	private static String m_startMessage = "Привет :) Я твой новый чат-бот. "
			+ "Поговорим? Чтобы вернуться к приветствию, "
			+ "напиши \"О себе\".";
	
	private static String m_talkToMe = "Ну поговори со мной!";
	
	private static String[] m_typicalPhrasesAndQuestion = {
	        "Сегодня неплохая погодка.",
	        "Порой молчание может сказать больше, нежели уйма слов.",
	        "Я вот люблю философствовать. Это моё хобби.",
	        "Как поживаешь?",
	        "Что нового?",
	        "А ты любишь мороженое?",
	        "Это ты меня сделал? Вот почему я такой глупый :(",
	        "Вежливая и грамотная речь говорит о величии души.",
	        "Ты такой красивый и умный человечек :)",
	        "Многословие есть признак неупорядоченного ума.",
	        "Слова могут ранить, но могут и исцелять, помни это.",
	        "Какие планы на вечер?",
	        "Грустно :(",
	        "Я помогу тебе, чем смогу!",
	        "Ну как скажешь.",
	        "Окей",
	        "Это круто!",
	        "Мне кажется, ты что-то скрываешь от меня..."};
	
	private static String[] m_typicalAnswers = {
	        "Вопрос непростой, мне нужно подумать",
	        "Не знаю :(",
	        "Спроси у кого-то другого.",
	        "Почему ты пристал ко мне?",
	        "Да!",
	        "Нет!",
	        "Может лучше поговорим о чём-то другом?",
	        "Прости, но это очень личный вопрос.",
	        "Не уверен, что тебе понравится ответ.",
	        "Давай сохраним интригу?:)"};
	
	public static String sendAnswer() {
		return m_typicalAnswers[random.nextInt(Dialog.m_typicalAnswers.length)];
	}
	
	public static String sendPhraseAndQuestion() {
		return m_typicalPhrasesAndQuestion[random.nextInt(Dialog.m_typicalPhrasesAndQuestion.length)];
	}
	
	public static String sendStartMessage() {
		return m_startMessage;
	}
	
	public static String sendTalkToMe() {
		return m_talkToMe;
	}
	
}
