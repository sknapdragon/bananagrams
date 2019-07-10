package ForwardBot;

public class FSM {
	public enum ForwardState {
		Idle, Initiazlize, Setup, DriveBound,OOBL,OOBR, Scoring
	}

	private ForwardState _state = ForwardState.Initiazlize; // local var that tracks the state
	// use this for init.

	void Start() {
		while(true) {
		switch (_state) {
		case Initiazlize:
			InitMe();
			break;
		case Setup:
			SetMeUp();
			break;
		case DriveBound:
			Driving();
			break;
		case OOBL:
		case OOBR:
		case Scoring:
			Scoring();
			break;
			

		}
		}
	}

	private void SetMeUp() {
		// TODO Auto-generated method stub
		System.out.println("this is set me up");
		_state = ForwardState.Setup;
	}
	private void InitMe() {
		System.out.println("Initmefunc");
	}
	private void Driving() {
		
	}
	private void Scoring() {
		
	}
	private void process

}