package teeest;

import CustomSensor.HiTechnicIRSeekerV2CustomStr;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class ProofOfConcept {
/*
 * 	static RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
	static RegulatedMotor mb = new EV3MediumRegulatedMotor(MotorPort.B);
	static RegulatedMotor mc = new EV3MediumRegulatedMotor(MotorPort.C);
	static Wheel wheela = WheeledChassis.modelHolonomicWheel(ma, 61).polarPosition(30,70 ).gearRatio(1);
	static Wheel wheelb = WheeledChassis.modelHolonomicWheel(mb, 61).polarPosition(150,70 ).gearRatio(1);
	static Wheel wheelc = WheeledChassis.modelHolonomicWheel(mc, 61).polarPosition(270,70 ).gearRatio(1);
	static Chassis chassis = new WheeledChassis(new Wheel[] { wheela, wheelb, wheelc }, WheeledChassis.TYPE_HOLONOMIC);
 */

	static RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
	static RegulatedMotor mb = new EV3MediumRegulatedMotor(MotorPort.B);
	static RegulatedMotor mc = new EV3MediumRegulatedMotor(MotorPort.C);
	static Wheel wheela = WheeledChassis.modelHolonomicWheel(ma, 61).polarPosition(30,70 ).gearRatio(1);
	static Wheel wheelb = WheeledChassis.modelHolonomicWheel(mb, 61).polarPosition(150,70 ).gearRatio(1);
	static Wheel wheelc = WheeledChassis.modelHolonomicWheel(mc, 61).polarPosition(270,70 ).gearRatio(1);
	static Chassis chassis = new WheeledChassis(new Wheel[] { wheela, wheelb, wheelc }, WheeledChassis.TYPE_HOLONOMIC);
	static Port s1 = LocalEV3.get().getPort("S1");
	static Port s2 = LocalEV3.get().getPort("S2");
	static Port s3 = LocalEV3.get().getPort("S3");
	static Port s4 = LocalEV3.get().getPort("S4");
	
	static SensorModes irSeeker = new HiTechnicIRSeekerV2CustomStr(s1);
	static SampleProvider iranglePro = irSeeker.getMode("Modulated");
	static float[] irAngles = new float[iranglePro.sampleSize()];
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
	
		chassis.setVelocity(0,0, 180);
		Delay.msDelay(3000);
		chassis.setVelocity(0, 0,0);
		Button.waitForAnyPress();

		operation(irAngles,iranglePro,chassis);
		
		

		long endTime = System.nanoTime();
		LCD.drawString("Took "+(endTime - startTime) + " ns", 1, 1); 
		Button.waitForAnyPress();
		chassis.setVelocity(500, 0, 0);
		Delay.msDelay(1000);
		chassis.setVelocity(500, 180, 0);
		Delay.msDelay(1000);
		chassis.setVelocity(500, 180, 0);
		Delay.msDelay(1000);
		chassis.setVelocity(500, 270, 0);
		Delay.msDelay(1000);
		
	}
	public static void operation(float [] irp,SampleProvider iap, Chassis c) {
		int angle = (int)irp[0];
		int state = 0;
		while(true) {
			iap.fetchSample(irp, 0);
			angle = (int) irp[0];
			switch (state) {
			//This switch takes in ir vales and does stuff with them.
			case 0:
				LCD.drawInt(angle, 1, 1);
				switch(angle) {
				case-60:
				case -30 :
					c.setVelocity(200, 0, 0);
					break;
				case -90:
					c.setVelocity(500, 0,0);
					break;
					default:
						c.setVelocity(0, 0 , 0);
				}
				state = 0;
				
			}
		}
		
		
		
	}
}
