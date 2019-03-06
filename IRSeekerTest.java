package teeest;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class IRSeekerTest {
	public static void main( String [] args) {
		int radius, wheeldia;
		double gratio;
		radius = 75;
		gratio = 5/3;
		wheeldia = 65;
		int angle;
		Wheel wheel1 = WheeledChassis.modelHolonomicWheel(Motor.B, wheeldia).polarPosition(30, 75).gearRatio(gratio);
		Wheel wheel2 = WheeledChassis.modelHolonomicWheel(Motor.C, wheeldia).polarPosition(150, 75).gearRatio(gratio);
		Wheel wheel3 = WheeledChassis.modelHolonomicWheel(Motor.A, wheeldia).polarPosition(270, 75).gearRatio(gratio);
    Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2, wheel3}, WheeledChassis.TYPE_HOLONOMIC);
    int spd = 300;
//	   chassis.travelCartesian(300, 300, 0);;
	//   Delay.msDelay(6000);
//	   chassis.setVelocity(spd, 90, 0);
	//   Delay.msDelay(4000);
	 //  chassis.setVelocity(spd, 270, 0);
	  // Delay.msDelay(4000);
    Port s4 = LocalEV3.get().getPort("S4");
    SensorModes irSeeker = new HiTechnicIRSeekerV2(s4);
	SampleProvider iranglePro = ((HiTechnicIRSeekerV2) irSeeker).getModulatedMode();

	float [] irangles = new float [iranglePro.sampleSize()];
		while (!Button.ESCAPE.isDown()) {
	        LCD.refresh();
	        LCD.clear();
			iranglePro.fetchSample(irangles, 0);
			LCD.drawInt((int) irangles [0], 2, 2);
			if (irangles[0] >= 0)
				chassis.setVelocity(0, 0, -irangles[0]);
			}
	}
	
	public static void displayIRangle(float [] ang) {
		LCD.drawInt((int) ang [0], 2, 2);
		Delay.msDelay(10);
		LCD.clearDisplay();
	}
	
}
