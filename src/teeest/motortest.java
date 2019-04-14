package teeest;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class motortest {
	public static void main(String [] args) {
	RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
	RegulatedMotor mb = new EV3MediumRegulatedMotor(MotorPort.B);
	RegulatedMotor mc = new EV3MediumRegulatedMotor(MotorPort.C);
	RegulatedMotor md = new EV3MediumRegulatedMotor(MotorPort.D);
	ma.forward();
	Delay.msDelay(5000);
	ma.stop();
	mb.forward();
	Delay.msDelay(5000);
	mb.stop();
	mc.forward();
	Delay.msDelay(5000);
	mc.stop();
	md.forward();
	Delay.msDelay(5000);
	md.stop();
	}
}
