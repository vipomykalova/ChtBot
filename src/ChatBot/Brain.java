package ChatBot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brain {

	public String currentGame;
	public FSM fsm = new FSM();
	private Hangman currentHangman;
	private TruthOrDare currentTruthOrDare;
	private Map<String, List<String>> mapOfData = new HashMap<>();

	public Brain() {
		fsm.setState(this::startMessage);
	}
	
	public Map<String, List<String>> startMessage(String input) {
		fsm.setState(this::gameSelection);
		mapOfData.clear();
		List<String> curListButtons = Arrays.asList("правда или действие :underage:",
				                                    "виселица :detective:",
				                                    "о себе :flushed:"); 
		mapOfData.put(Dialog.INSTANCE.getString("приветствие"), curListButtons);
		return mapOfData;
	}
	
	public Map<String, List<String>> gameSelection(String input) {
		mapOfData.clear();
		List<String> curListButtons;
		if (input.startsWith("виселица")) {
			fsm.setState(this::hangmanWordGeneration);
			currentGame = "виселица";
			curListButtons = Arrays.asList("ДА:fire:");
			mapOfData.put(Dialog.INSTANCE.getString("начало"), curListButtons);
		}
		else if (input.startsWith("правда или действие")) {
			fsm.setState(this::truthOrDareGetNames);
			currentGame = "правда или действие";
			curListButtons = Arrays.asList("ДА:fire:");
			mapOfData.put(Dialog.INSTANCE.getString("начало"), curListButtons);
		}
		else if (input.startsWith("о себе")) {
			fsm.setState(this::gameSelection);
			curListButtons = Arrays.asList("правда или действие :underage:",
					                       "виселица :detective:",
					                       "о себе :flushed:"); 
			mapOfData.put(Dialog.INSTANCE.getString("приветствие"), curListButtons);
		}
		else {
			fsm.setState(this::gameSelection);
			curListButtons = Arrays.asList("правда или действие :underage:",
					                       "виселица :detective:",
					                       "о себе :flushed:"); 
			mapOfData.put(Dialog.INSTANCE.getString("некорректный ввод"), curListButtons);
		}
		return mapOfData;
	}
	
	public Map<String, List<String>> hangmanWordGeneration(String input) {
		mapOfData.clear();
		currentHangman = new Hangman(this);
		fsm.setState(currentHangman::hangmanGame);
		List<String> curListButtons = Arrays.asList("о себе :flushed:",
				                                    "стоп :no_entry:");
		mapOfData.put(currentHangman.setWord(), curListButtons);
		return mapOfData;
	}
	
	public Map<String, List<String>> truthOrDareGetNames(String input) {
		mapOfData.clear();
		fsm.setState(this::truthOrDareParseNames);
		List<String> curListButtons = Arrays.asList();
		mapOfData.put(Dialog.INSTANCE.getString("игроки"), curListButtons);
		return mapOfData;
	}
	
	public Map<String, List<String>> truthOrDareParseNames(String input) {
		mapOfData.clear();
		currentTruthOrDare = new TruthOrDare(this);
		currentTruthOrDare.parseNames(input);
		fsm.setState(currentTruthOrDare::truthOrDareGame);
		List<String> curListButtons = Arrays.asList("правда :zipper_mouth:",
				                                    "действие :tongue:",
				                                    "о себе :flushed:",
				                                    "стоп :no_entry:"); 
		mapOfData.put(currentTruthOrDare.askPlayer(), curListButtons);
		return mapOfData;
	}
	
	public Map<String, List<String>> reply(String input) {
		return fsm.update(input);
	}

}
