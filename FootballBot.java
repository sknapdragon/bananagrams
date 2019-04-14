package teeest;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class FootballBot {
	private static DataExchange EXC;

	public static void main(String[] args) {
		EXC = new DataExchange();
		int currentangle;
		int rad = 75;
		int wdia = 65; // in millimeters
		float turn = 0;
		double gratio = 1.6666;
		RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
		RegulatedMotor mb = new EV3MediumRegulatedMotor(MotorPort.B);
		RegulatedMotor mc = new EV3MediumRegulatedMotor(MotorPort.C);
		RegulatedMotor md = new EV3MediumRegulatedMotor(MotorPort.D);
		Wheel wheela = createWheel(mb, wdia, 30, rad, gratio);
		Wheel wheelc = createWheel(mc, wdia, 150, rad, gratio);
		Wheel wheelb = createWheel(ma, wdia, 270, rad, gratio);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheela, wheelb, wheelc }, WheeledChassis.TYPE_HOLONOMIC);
		// setting up sensors
		Port s1 = LocalEV3.get().getPort("S1");
		Port s2 = LocalEV3.get().getPort("S2");
		Port s3 = LocalEV3.get().getPort("S3");
		Port s4 = LocalEV3.get().getPort("S4");
		SensorModes irSeeker = new HiTechnicIRSeekerV2(s4);
		// SensorModes colorSensor = new EV3ColorSensor(s1);
		SensorModes compassSensor = new HiTechnicCompass(s2);
		SampleProvider iranglePro = irSeeker.getMode("Modulated");
		// SampleProvider colorPro = colorSensor.getMode("RGB");
	//	SampleProvider distancePro = usonicSensor.getMode("Distance");
		SampleProvider compdistancePro = compassSensor.getMode("Angle");
		// the ARRAYS
		float[] irAngles = new float[iranglePro.sampleSize()];
		// float[] colors = new float[colorPro.sampleSize()];
	//	float[] dists = new float[distancePro.sampleSize()];
		float[] cAngles = new float[compdistancePro.sampleSize()];
		// activating PID thread
		PID Porpoise = new PID(EXC, compdistancePro);
		Porpoise.start();
		// main program
		float lDir = 0, lSpd = 70;
		while (!Button.ESCAPE.isDown()) {
			turn = 0 + Porpoise.getCorrection();
			iranglePro.fetchSample(irAngles, 0);
			setDirection(irAngles[0]);
	        LCD.refresh();
	        LCD.clear();
			LCD.drawInt((int) irAngles [0], 2, 2);


			chassis.setVelocity(lDir, lSpd, turn);
		}

		Porpoise.stopPID();
	}

	public static Wheel createWheel(RegulatedMotor m, int wheeldia, int pos, int radi, double gratio) {
		return WheeledChassis.modelHolonomicWheel(m, wheeldia).polarPosition(pos, radi).gearRatio(gratio);
	}

	public static void moveBot(int spd, Chassis chassis) {
		chassis.setVelocity(spd, 0, 0);
	}

	public static void moveBot(int spd, int direction, int angvel, Chassis chassis) {
		chassis.setVelocity(spd, direction, angvel);
	}

	public static float setDirection(float irAng) {
		int dir = 0;
		
		switch ((int)irAng) {
		case 0:
			dir = -15;
			break;
		case 1:
			dir = -15;
			break;
		case 4124:
			dir = -15;
			break;
		case 2:
			dir = -15;
			break;
		case 3:
			dir = -15;
			break;
		case 4:
			dir = -15;
			break;
		case 5:
			dir = -15;
			break;
		case 8:
			dir = -15;
			break;
		case 180:
			dir = -15;
			break;
		case 1230:
			dir = -15;
			break;

		}
		return dir;

	}

}
