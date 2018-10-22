package ChatBot;

import Hangman.Hangman;
import TruthOrDare.TruthOrDare;

public class Brain {
	
	public FSM fsm = new FSM();
	Hangman currentHangman;
    TruthOrDare currentTruthOrDare;

	public Brain() {
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
