package teeest;

import java.util.Arrays;

import CustomSensor.HiTechnicIRSeekerV2CustomStr;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.HiTechnicColorSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class IRSeekerTest {
	public static void main(String[] args) {
		int radius, wheeldia;
		double gratio;
		radius = 75;
		gratio = 5 / 3;
		wheeldia = 65;
		int angle;
		Wheel wheel1 = WheeledChassis.modelHolonomicWheel(Motor.B, wheeldia).polarPosition(30, 75).gearRatio(gratio);
		Wheel wheel2 = WheeledChassis.modelHolonomicWheel(Motor.C, wheeldia).polarPosition(150, 75).gearRatio(gratio);
		Wheel wheel3 = WheeledChassis.modelHolonomicWheel(Motor.A, wheeldia).polarPosition(270, 75).gearRatio(gratio);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2, wheel3 }, WheeledChassis.TYPE_HOLONOMIC);
		int spd = 300;
//	   chassis.travelCartesian(300, 300, 0);;
		// Delay.msDelay(6000);
//	   chassis.setVelocity(spd, 90, 0);
		// Delay.msDelay(4000);
		// chassis.setVelocity(spd, 270, 0);
		// Delay.msDelay(4000);
		Port s1 = LocalEV3.get().getPort("S1");
		Port s2 = LocalEV3.get().getPort("S2");
		Port s3 = LocalEV3.get().getPort("S3");
		Port s4 = LocalEV3.get().getPort("S4");
		SensorModes irSeeker = new HiTechnicIRSeekerV2CustomStr(s2);
		SensorModes colorSensor = new HiTechnicColorSensor(s3);
		SensorModes compassSensor = new HiTechnicCompass(s1);
		SampleProvider iranglePro = irSeeker.getMode("Modulated");
		SampleProvider colorPro = colorSensor.getMode("RGB");
//	SampleProvider distancePro = usonicSensor.getMode("Distance");
		SampleProvider compdistancePro = compassSensor.getMode("Angle");
		SampleProvider irStrPro = irSeeker.getMode("ModulatedMiddleStrength");
		// the ARRAYS
		float[] irAngles = new float[iranglePro.sampleSize()];
		float[] colorReadings = new float[colorPro.sampleSize()];
//	float[] dists = new float[distancePro.sampleSize()];
		float[] cAngles = new float[compdistancePro.sampleSize()];
		float[] irStr = new float[irStrPro.sampleSize()];
		float[] irangles = new float[iranglePro.sampleSize()];
		while (!Button.ESCAPE.isDown()) {
			iranglePro.fetchSample(irAngles, 0);
			irStrPro.fetchSample(irStr, 0);
			colorPro.fetchSample(colorReadings, 0);
			// setDirection(irAngles[0]);
			LCD.refresh();
			LCD.clear();
			LCD.drawInt((int) irAngles[0], 2, 2);
			LCD.drawInt((int) irStr[0], 2, 4);
			LCD.drawString(Arrays.toString(colorReadings), 2, 6);

		}
	}

	public static void displayIRangle(float[] ang) {
		LCD.drawInt((int) ang[0], 2, 2);
		Delay.msDelay(10);
		LCD.clearDisplay();
	}

}
