package teeest;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.*;
import lejos.utility.Delay;
import lejos.hardware.ev3.LocalEV3;
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
		Wheel wheel1 = WheeledChassis.modelHolonomicWheel(Motor.B, 65).polarPosition(30, 75);
		Wheel wheel2 = WheeledChassis.modelHolonomicWheel(Motor.C, 65).polarPosition(150, 75);
		Wheel wheel3 = WheeledChassis.modelHolonomicWheel(Motor.A, 65).polarPosition(270, 75);
    Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2, wheel3}, WheeledChassis.TYPE_HOLONOMIC);
    int spd = 300;
//	   chassis.travelCartesian(300, 300, 0);;
	//   Delay.msDelay(6000);
//	   chassis.setVelocity(spd, 90, 0);
	//   Delay.msDelay(4000);
	 //  chassis.setVelocity(spd, 270, 0);
	  // Delay.msDelay(4000);
    	chassis.setVelocity(spd, 0, 0);
	   Delay.msDelay(4000);
	}
}
 // get a port instance
    
