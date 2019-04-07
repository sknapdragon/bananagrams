package teeest;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
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
	public static void main(String[] args) {
		int currentangle;
		int rad = 75;
		int wdia = 65; // in millimeters
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
		SensorModes colorSensor = new EV3ColorSensor(s2);
		SensorModes usonicSensor = new EV3UltrasonicSensor(s3);
		SensorModes compassSensor = new HiTechnicCompass(s1);
		SampleProvider iranglePro = ((HiTechnicIRSeekerV2) irSeeker).getModulatedMode();
		SampleProvider colorPro = colorSensor.getMode("RGB");
		SampleProvider distancePro = usonicSensor.getMode("Distance");
		SampleProvider compdistancePro = compassSensor.getMode("Angle");
		// the ARRAYS
		float[] irAngles = new float[iranglePro.sampleSize()];
		float[] colors = new float[iranglePro.sampleSize()];
		float[] dists = new float[iranglePro.sampleSize()];
		float[] cAngles = new float[iranglePro.sampleSize()];
		// main program
		while (!Button.ESCAPE.isDown()) {
			iranglePro.fetchSample(irAngles, 0);
			currentangle = (int) irAngles[0];
			switch (currentangle) {
			case 0:
				moveBot(100, chassis);
				break;
			case 30:
			case 330:
				moveBot(100, currentangle, 0, chassis);
				break;
			}

		}
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

	protected static float angleCorrect(SampleProvider compdistancePro, float[] compassangles, float start,
			float previouscorrection) {

		float kp, ki, kd;
		float errorsum = 0;
		float slope;
		float error = 0;
		float lasterror;
		while (true) {
			compdistancePro.fetchSample(compassangles, 0);
			kp = 10;
			// remember why writing this as a method doesn't 'work' rn
			ki = 10;
			kp = 10;

			lasterror = error;
			error = start - compassangles[0];
			errorsum += error;
			slope = error - lasterror;
			float correction = kp * error + ki * errorsum + kp * slope;
			return correction;
		}
	}
}
