package teeest;

import java.util.Arrays;

import CustomSensor.HiTechnicIRSeekerV2CustomStr;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.HiTechnicColorSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class FootballBot {
	private static DataExchange EXC;

	public static void main(String[] args) {
		EXC = new DataExchange();
		int currentangle;
		int radf = 72; int radb = 69;
		int wdiaf = 61; // in millimeters
		int wdiab = 124;
		float turn = 0;
		double gratio = 1.6666;
		RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
		RegulatedMotor mb = new EV3MediumRegulatedMotor(MotorPort.B);
		RegulatedMotor mc = new EV3MediumRegulatedMotor(MotorPort.C);
		RegulatedMotor md = new EV3MediumRegulatedMotor(MotorPort.D);
		Wheel wheela = createWheel(mc, wdiaf, 30, radf, gratio);
		Wheel wheelc = createWheel(mb, wdiaf, 150, radf, gratio);
		//back wheel
		Wheel wheelb = createWheel(ma, 124, 270, radb, .6);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheela, wheelb, wheelc }, WheeledChassis.TYPE_HOLONOMIC);
		// setting up sensors
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
		// activating PID thread
		Button.waitForAnyPress();
		PID Porpoise = new PID(EXC, compdistancePro);
		Porpoise.start();
		// main program
		float lDir = 0, lSpd = 1500;
		while (!Button.ESCAPE.isDown()) {
			turn = 0 + Porpoise.getCorrection();
			iranglePro.fetchSample(irAngles, 0);
			irStrPro.fetchSample(irStr, 0);
			colorPro.fetchSample(colorReadings, 0);
			//setDirection(irAngles[0]);
	        LCD.refresh();
	        LCD.clear();
			LCD.drawInt((int) irAngles [0], 2, 2);
			LCD.drawInt((int) irStr [0], 2, 4);
			LCD.drawString(Arrays.toString(colorReadings), 2, 6);
			LCD.drawInt((int) chassis.getMaxLinearSpeed(), 2, 8);
			/*

		lDir = setDirection((int)irAngles[0], (int)irStr[0]);
			//put turn there
			chassis.setVelocity(lSpd, lDir, turn); */
			chassis.travelCartesian(400, 0, 0);
		}

		Porpoise.stopPID();
	}

	public static Wheel createWheel(RegulatedMotor m, int wheeldia, int pos, int radi, double gratio) {
		return WheeledChassis.modelHolonomicWheel(m, wheeldia).polarPosition(pos, radi).gearRatio(gratio);
	}


	public static float setDirection(float irAng, int irStr) {
	 	int dir = 90;
		
		switch (-(int)irAng) {
		case 0:
			if (irStr <30)
				dir -= 180;
			else
			dir +=0;
			break;
		case 30:
			dir += 40;
			break;
		case 60:
			dir += 75;
			break;
		case 90:
			dir += 110;
			break;
		case 120:
			dir +=135;
			break;
		case -30:
			dir += 45;
			break;
		case -60:
			dir += -75;
			break;
		case -90:
			dir += -110;
			break;
		case -120:
			dir += -135;
			break;


		}
		return dir;

	}

}
