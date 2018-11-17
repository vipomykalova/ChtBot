package main.java;

@FunctionalInterface
interface ActiveState {
	public BotAnswer doWork(String input);
}

public class FSM {
	public ActiveState activeState;
	
	public void setState(ActiveState act) {
		activeState = act;
	}
	
	public BotAnswer update(String input) {
		return activeState.doWork(input);
	}
	
}
