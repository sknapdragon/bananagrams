package tempProject;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class QuickFix extends Thread {
	int bAng = 0; // This angle starts at the regular Cartesian 0 degrees
	int baseSpeed = 400;
	CalcCal cal;
	RelevantProviders prov;
	PID pid;
	boolean on = true;
	String cur = "drive";

	boolean compensateOn = true;

	public QuickFix(CalcCal cals, RelevantProviders provis, PID pid) {
		this.cal = cals;
		prov = provis;
		this.pid = pid;
	}

	public void run() {
		Sound.beep();
		Sound.beep();

		while (on) {
			Driving();

		}
	}

	private void Driving() {
		int speedCons = 1;
		double speedmod = cal.estSpeed() * speedCons;
		double bDir = cal.estDirection();
		BallData.CartVelocity ballVel = new BallData.CartVelocity(speedmod, bDir);


		while (cur == "drive" && on) {

			prov.updateIrAng();
			int ang = fromFronttoCaresianAngleSystem((int) prov.irAngles[0]);
			switch (ang) {
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
				} else {
				bAng = 90;
				baseSpeed = 600;
				}
				Sound.beep();
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
				prov.chassis.setVelocity(baseSpeed, bAng, pid.getCorrection());
				//prov.chassis.setVelocity(sumVel.getSpeed(), sumVel.getVelDirection(), pid.getCorrection());
			else
				prov.chassis.setVelocity(baseSpeed, bAng, pid.getCorrection());
			if (prov.colorReadings[2] > 30) {
				cur = "scoring";
			}

		}

	}

	private void Scoring() {
		int direction; // Cartesian
		boolean lockedIn = false;
		double tgtAngle = 0;
		double speed = 600;
		while (prov.colorReadings[2] > 30) {
			prov.updateDistance();
			float distFromR = prov.maxdistances[0];
			double distXfromC = distFromR - .915;
			if (!lockedIn) {
				if (distXfromC < -.030) {
					tgtAngle = 180 / Math.PI * Math.atan2(.6, distXfromC);
				} else if (distXfromC > .30) {
					tgtAngle = 180 / Math.PI * Math.atan2(.6, distXfromC);
				} else {
					tgtAngle = 0;
				}
				lockedIn = true;
				pid.setRelativeTarget(-tgtAngle);
			}
			prov.chassis.setVelocity(speed, 0, pid.getCorrection());
		}
		pid.restartTarget();
		cur = "drive";
	}

	private int fromFronttoCaresianAngleSystem(int weirdAngle) { // adds ninety haHAA
		return weirdAngle + 90;
	}

	public void stopOP() {
		on = false;
	}

	public boolean isCompensateOn() {
		return compensateOn;
	}
}
