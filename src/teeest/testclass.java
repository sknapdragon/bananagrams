package teeest;

import lejos.hardware.Button;
import lejos.hardware.Key;
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
import lejos.utility.Delay;

public class testclass {
	public static void main(String[] args) {
		Wheel wheel1 = WheeledChassis.modelHolonomicWheel(new EV3MediumRegulatedMotor(MotorPort.B), 65).polarPosition(30, 75).gearRatio(1.66666);
		Wheel wheel2 = WheeledChassis.modelHolonomicWheel(new EV3MediumRegulatedMotor(MotorPort.A), 65).polarPosition(270, 75).gearRatio(1.66666);
		Wheel wheel3 = WheeledChassis.modelHolonomicWheel(new EV3MediumRegulatedMotor(MotorPort.C), 65).polarPosition(150, 75).gearRatio(1.66666);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2, wheel3 }, WheeledChassis.TYPE_HOLONOMIC);
		RegulatedMotor techMotor = new EV3MediumRegulatedMotor(MotorPort.D);
		

		Port s1 = LocalEV3.get().getPort("S1");
	    Port s2 = LocalEV3.get().getPort("S2");
	    Port s3 = LocalEV3.get().getPort("S3");
	    Port s4 = LocalEV3.get().getPort("S4");
	    SensorModes irSeeker = new HiTechnicIRSeekerV2(s4);
//	    SensorModes colorSensor = new EV3ColorSensor(s2);
//	    SensorModes usonicSensor = new EV3UltrasonicSensor(s3);
//	    SensorModes compassSensor = new HiTechnicCompass(s1);
		SampleProvider iranglePro = ((HiTechnicIRSeekerV2) irSeeker).getModulatedMode();
//		SampleProvider colorPro = colorSensor.getMode("RGB");
		//SampleProvider distancePro = usonicSensor.getMode("Distance");
	//	SampleProvider compdistancePro = compassSensor.getMode("Angle");
		float [] irangles = new float [iranglePro.sampleSize()];
	//	float [] colors = new float [iranglePro.sampleSize()];
	//	float [] distances = new float [iranglePro.sampleSize()];
	//	float [] compassangles = new float [iranglePro.sampleSize()];
		
		operation(chassis, iranglePro, irangles);
	}
	// All of the whole code in this package needs to be cleaned up and made structured. this shouldn't be written like this.
	protected static float angleCorrect(SampleProvider compdistancePro,float [] compassangles, float start , float previouscorrection) {

		float kp,ki,kd;
		float errorsum = 0; float slope; float error=0;float lasterror;
		while(true){
			compdistancePro.fetchSample(compassangles, 0);
		kp = 10;
		//remember why writing this as a method doesn't 'work' rn
		ki = 10;
		kp = 10;

		lasterror = error;
		error = start - compassangles[0];
		errorsum += error;
		slope = error - lasterror;
		float correction = kp*error + ki * errorsum + kp*slope;
		return correction;
		}
	}

	private static void operation(Chassis chassis, SampleProvider iranglePro, float[] irangles) {
		while (!Button.ESCAPE.isDown()) {
			iranglePro.fetchSample(irangles, 0);
			LCD.drawInt((int)irangles[0], 1, 1 );
			LCD.clear();
			if(irangles[0] > 100) {
				chassis.setVelocity(300, 180, 0);
			} else if (irangles[0] < 80){
				chassis.setVelocity(300,0, 0);
			} else {
				chassis.setVelocity(0, 0,0);
			}
		}
	}
}
