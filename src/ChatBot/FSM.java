package ChatBot;

@FunctionalInterface
interface ActiveState {
	public String doWork(String input);
}

public class FSM {
	ActiveState activeState;
	
	public void setState(ActiveState act) {
		activeState = act;
	}
	
	public String update(String input) {
		return activeState.doWork(input);
	}
}
