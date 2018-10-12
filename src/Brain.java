import Hangman.Hangman;
import TruthOrDare.TruthOrDare;

public class Brain {
	
	FMS fsm = new FMS();
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
		currentHangman = new Hangman();
		fsm.setState(this::hangmanGame);
		return currentHangman.setWord();
	}
	
	public String hangmanGame(String input) {    
		String result = currentHangman.currentResult(input);
		
		switch(currentHangman.currentStateGame) {
		case Win:
			fsm.setState(this::wantMore);
			return result;
		case Fail:
			fsm.setState(this::wantMore);
			return result;
		case Game:
			fsm.setState(this::hangmanGame);
			return result;
		case Stop:
		    fsm.setState(this::startMessage);
		    return Dialog.INSTANCE.getString("прощание"); 
		}	
		return null;
	}
	
	public String wantMore(String input) {
		switch(input) {
		case "да":
			fsm.setState(this::hangmanWordGeneration);
			return Dialog.INSTANCE.getString("начало");	
		case "нет":
			fsm.setState(this::startMessage);
			return Dialog.INSTANCE.getString("прощание");
		case "о себе":
			fsm.setState(this::gameSelection);
			return Dialog.INSTANCE.getString("приветствие");		
		}
		fsm.setState(this::wantMore);
		return Dialog.INSTANCE.getString("некорректный ввод");
	}
	
	public String truthOrDareGetNames(String input) {
		fsm.setState(this::truthOrDareParseNames);
		return Dialog.INSTANCE.getString("игроки");
	}
	
	public String truthOrDareParseNames(String input) {
		currentTruthOrDare = new TruthOrDare();
		currentTruthOrDare.parseNames(input);
		fsm.setState(this::truthOrDareGame);
		return currentTruthOrDare.askPlayer();
	}
	
	public String truthOrDareAskPlayer(String input) {
		currentTruthOrDare.checkState(input);
		if (currentTruthOrDare.currentStateGame == TruthOrDare.StatesGame.Stop) {
			fsm.setState(this::startMessage);
			return Dialog.INSTANCE.getString("прощание");
		}
		fsm.setState(this::truthOrDareGame);
		return currentTruthOrDare.askPlayer();
	}
	
	public String truthOrDareGame(String input) {
		String result = currentTruthOrDare.taskForPlayer(input);
		
		switch(currentTruthOrDare.currentStateGame) {
		case Correct:
			fsm.setState(this::truthOrDareAskPlayer);
			return result;
		case Incorrect:
			fsm.setState(this::truthOrDareGame);
			return Dialog.INSTANCE.getString("некорректный ввод");
		case Stop:
			fsm.setState(this::startMessage);
			return Dialog.INSTANCE.getString("прощание");
		}	
		return null;
	}
	
	public String reply(String input) {
		return fsm.update(input);
	}

}
