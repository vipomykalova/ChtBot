
@FunctionalInterface
interface ActiveState {
	public void doWork();
}

public class FMS {
	ActiveState activeState;
	
	
	public void setState(ActiveState act) {
		activeState = act;
	}
	
	public void update() {
		if (activeState != null) {
			activeState.doWork();
		}
	}

}
