package ChatBot;

@FunctionalInterface
interface ActiveState {
	public String doWork(String input);
}

public class FMS {
	ActiveState activeState;
	
	public void setState(ActiveState act) {
		activeState = act;
	}
	
	public String update(String input) {
		return activeState.doWork(input);
	}
}
