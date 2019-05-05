package teeest;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.EncoderMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class testclass {
	public static void main(String[] args) {

		EncoderMotor c = new UnregulatedMotor(MotorPort.C);
		EncoderMotor b = new UnregulatedMotor(MotorPort.B);
		EncoderMotor a = new UnregulatedMotor(MotorPort.A);

		b.setPower(25);
		c.setPower(25);
		a.setPower(18);
		c.backward();
		b.backward();
		a.forward();
		Delay.msDelay(10000);
		
		Port s1 = LocalEV3.get().getPort("S1");
	    Port s2 = LocalEV3.get().getPort("S2");
	    Port s3 = LocalEV3.get().getPort("S3");
	    Port s4 = LocalEV3.get().getPort("S4");
	    
	}

}
