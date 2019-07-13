package tempProject;

import lejos.hardware.Sound;

public class FSM extends Thread {
	public enum ForwardState {
		Idle, Initiazlize, Setup, DriveBound, OOBL, OOBR, Scoring
	}

	private ForwardState _state = ForwardState.Initiazlize; // local var that tracks the state
	// use this for init.

	CalcCal cal;
	RelevantProviders prov;
	PID pid;
	boolean on = true;

	boolean compensateOn = true;

	public FSM(CalcCal cals, RelevantProviders provis, PID pid) {
		this.cal = cals;
		prov = provis;
		this.pid = pid;
	}

	public void run() {

		while (on) {
			Sound.beep();
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
			default:
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
		_state = ForwardState.DriveBound;
	}

	private void Driving() {
		int speedCons = 1;
		double speedmod = cal.estSpeedInstantously() * speedCons;
		double bDir = cal.estDirection();
		BallData.CartVelocity ballVel = new BallData.CartVelocity(speedmod, bDir);
		int bAng = 0; // This angle starts at the regular Cartesian 0 degrees
		int baseSpeed = 400;

		while (_state == ForwardState.DriveBound && on) {
			prov.updateIrAng();
			switch (fromFronttoCaresianAngleSystem((int) prov.cAngles[0]) % 360) {
			case 210: // to the right
				bAng = 240;
				baseSpeed = 400;
				break;
			case 180:
				bAng = 200;
				baseSpeed = 400;
				break;
			case 150:
				bAng = 180;
				baseSpeed = 400;
				break;
			case 120:
				bAng = 140;
				baseSpeed = 300;
				break;
			case 90: // the weird system's 0
				if (prov.irStr[2] < 10) {
					baseSpeed = 600;
							bAng = 280;
				}
				bAng = 0;
				baseSpeed = 600;
				break;
			case 60:
				bAng = 40;
				baseSpeed = 300;
				break;
			case 30:
				bAng = 10;
				baseSpeed = 400;
				break;
			case 0: // the Cartesian 0
				bAng = 340;
				baseSpeed = 400;
				break;
			case -30:
			case 330:
				bAng = 300;
				baseSpeed = 400;
				break;
			}
			BallData.CartVelocity chassisVel = new BallData.CartVelocity(baseSpeed, bAng);
			BallData.CartVelocity sumVel = chassisVel.addVelocities(ballVel);
			if (compensateOn)
			prov.chassis.setVelocity(sumVel.getSpeed(), sumVel.getVelDirection(), pid.getCorrection());
			else
				prov.chassis.setVelocity(baseSpeed, bAng, pid.getCorrection());
			if ( prov.colorReadings[2]>30) {
				_state = ForwardState.Scoring;
			}
		}

	}

	private void Scoring() {
		int direction; //Cartesian
		boolean lockedIn = false;
		double tgtAngle = 0;
		double speed = 600;
		while (prov.colorReadings[2]>30) {
			prov.updateDistance();
			float distFromR = prov.maxdistances[0];
			double distXfromC = distFromR - .915;
			if (!lockedIn) {
			if (distXfromC < -.030) {
				tgtAngle = 180/Math.PI * Math.atan2(.6,distXfromC);
			}else if (distXfromC > .30) {
				tgtAngle =  180/Math.PI * Math.atan2(.6,distXfromC);
			}
			else {
				tgtAngle = 0;
			}
			lockedIn = true;
			pid.setRelativeTarget(-tgtAngle);
			}
				prov.chassis.setVelocity(speed, 0, pid.getCorrection());
		}
		pid.restartTarget();
		_state = ForwardState.DriveBound;
	}

	private int fromFronttoCaresianAngleSystem(int weirdAngle) { // adds ninety haHAA
		return weirdAngle + 90;
	}
	public void stopOP (){
		on = false;
	}
	public boolean isCompensateOn() {
		return compensateOn;
	}

	public void setCompensateOn(boolean compensateOn) {
		this.compensateOn = compensateOn;
	}
}