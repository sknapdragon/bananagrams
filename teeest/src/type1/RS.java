package type1;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.Sound;
import lejos.robotics.chassis.Chassis;

public class RS extends Thread {
	Chassis c;
	RelevantProviders prov;
	PID PID;
	boolean on = true;
	public RS(Chassis Chass, RelevantProviders pr, PID pi) {
		c = Chass;
		prov = pr;
		PID = pi;
	}

	public void stopOP() {
		on = false;
	}
	public void run() {
		String state = "Neutral";


		while (on){
			int blue = (int)(255* prov.colorReadings[2]);
		float correction = PID.getCorrection();
		int angle = (int) prov.irAngles[0];
		switch (state) {
		// This switch takes in IR values and does stuff with them.
		case "Neutral":

			switch (angle) {
			case 0:
				//directly in front
				if (prov.irStr[2]<20) // fine tune the threshhold
					c.setVelocity(500, 270, correction); //if signal not strong enough, go backwards
				else
				c.setVelocity(400, 90, correction);
				break;
			case -60:
				c.setVelocity(400, 0, correction);
				break;
			case -30: // to the "right"
				c.setVelocity(300, 30, correction);
				break;
			case -90:
				c.setVelocity(500, -30, correction);
				break;
			case -120:
				c.setVelocity(500, -90, correction);
				break;
			case 120: 
				c.setVelocity(500, -90, correction);
				break;
			case 90:
				c.setVelocity(500, -150, correction);
				break;
			case 60:
				c.setVelocity(500, 180, correction);
				break;
			case 30: // to the "left"
				c.setVelocity(500, 150, correction);
				break;
			default:
				c.setVelocity(0, 0, correction);
			}
			if (blue > 25)
				state = "HasTheBall";
			else
			state = "Neutral";


			break;
			case "HasTheBall":
					Sound.twoBeeps();
					prov.md.rotate(60);
					c.setVelocity(400, 90, correction);

				//experiment with remote here later
				if (blue < 25) { 
					prov.md.flt();
					state = "Neutral";
				}
				
				break;
		}

	}

}
}