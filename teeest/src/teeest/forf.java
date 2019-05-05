package teeest;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.*;
import lejos.utility.Delay;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
//import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
//import lejos.hardware.port.Port;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
public class forf {
	public static void main( String [] args) {
		int radius, wheeldia;
		double gratio;
		radius = 75;
		gratio = 5/3;
		wheeldia = 65;
			int currentangle;
		int rad = 75;
		int wdia = 65; // in millimeters
		float turn = 0;
		RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
		RegulatedMotor mb = new EV3MediumRegulatedMotor(MotorPort.B);
		RegulatedMotor mc = new EV3MediumRegulatedMotor(MotorPort.C);
		RegulatedMotor md = new EV3MediumRegulatedMotor(MotorPort.D);
		
		Wheel wheela = createWheel(mb, wdia, 30, rad, gratio);
		Wheel wheelc = createWheel(mc, wdia, 150, rad, gratio);
		Wheel wheelb = createWheel(ma, wdia, 270, rad, gratio);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheela, wheelb, wheelc }, WheeledChassis.TYPE_HOLONOMIC);
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
	while(true)
    	chassis.setVelocity(spd, 270, 0);

	}
	
	public static void displayIRangle(float [] ang) {
		LCD.drawInt((int) ang [0], 2, 2);
		LCD.clearDisplay();
	}
	public static Wheel createWheel(RegulatedMotor m, int wheeldia, int pos, int radi, double gratio) {
		return WheeledChassis.modelHolonomicWheel(m, wheeldia).polarPosition(pos, radi).gearRatio(gratio);
	}
	
}
 // get a port instance
    
