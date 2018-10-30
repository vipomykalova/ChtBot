package ChatBot;

import java.util.List;
import java.util.Map;

@FunctionalInterface
interface ActiveState {
	public Map<String, List<String>> doWork(String input);
}

public class FSM {
	public ActiveState activeState;
	
	public void setState(ActiveState act) {
		activeState = act;
	}
	
	public Map<String, List<String>> update(String input) {
		return activeState.doWork(input);
	}
	
}
