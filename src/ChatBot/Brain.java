package ChatBot;

public class Brain {
	
	public FMS fsm = new FMS();
	Hangman currentHangman;
	TruthOrDare currentTruthOrDare;

	Brain() {
		fsm.setState(this::startMessage);
	}
	
	public String startMessage(String input) {
		fsm.setState(this::gameSelection);
		return Dialog.INSTANCE.getString("приветствие");
	}
	
	public String gameSelection(String input) {
		switch(input) {
		case "виселица":
			fsm.setState(this::hangmanWordGeneration);
			return Dialog.INSTANCE.getString("начало");
		case "правда или действие":
			fsm.setState(this::truthOrDareGetNames);
			return Dialog.INSTANCE.getString("начало");	
		case "о себе":
			fsm.setState(this::gameSelection);
			return Dialog.INSTANCE.getString("приветствие");
		}
		fsm.setState(this::gameSelection);
		return Dialog.INSTANCE.getString("некорректный ввод");
	}
	
	public String hangmanWordGeneration(String input) {
		currentHangman = new Hangman(this);
		fsm.setState(currentHangman::hangmanGame);
		return currentHangman.setWord();
	}
	
	public String truthOrDareGetNames(String input) {
		fsm.setState(this::truthOrDareParseNames);
		return Dialog.INSTANCE.getString("игроки");
	}
	
	public String truthOrDareParseNames(String input) {
		currentTruthOrDare = new TruthOrDare(this);
		currentTruthOrDare.parseNames(input);
		fsm.setState(currentTruthOrDare::truthOrDareGame);
		return currentTruthOrDare.askPlayer();
	}
	
	public String reply(String input) {
		return fsm.update(input);
	}

}
