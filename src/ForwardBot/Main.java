package ForwardBot;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Main {


	public static void main(String[] args) {
		//These providers are refreshed to be pulled from
		//the data members are public because I am lazy\
		RelevantProviders prov;
		DataExchange Exc;
		prov = new RelevantProviders();
		Exc = new DataExchange(); //exchanges data properly between the pid and the Operation thread
		CalcCal cals = new CalcCal(1000,prov);
		PID Porpoise = new PID(Exc, prov.cAngles); //PID which realigns robo's angle
		FSM states = new FSM(cals, prov, Porpoise);

		

		LCD.drawString("hold up to disable compensation", 1, 1);
		Button.waitForAnyPress();
		if (Button.UP.isDown())
			states.setCompensateOn(false);
		Delay.msDelay(500);
		
		Button.waitForAnyPress();
		Porpoise.start();
		Button.waitForAnyPress();
		cals.start();
		states.start();
		
		while (!Button.ESCAPE.isDown()) {


		// PID test used in day 1	prov.chassis.setVelocity(0, 0, Porpoise.getCorrection());
			//draws stuff
			LCD.drawInt((int) prov.irAngles [0], 2, 0);
			LCD.drawInt((int) prov.irStr [2], 2, 1);
			LCD.drawInt(prov.avgStr(), 6, 1);
			LCD.drawInt((int) (255*prov.colorReadings[2]), 2, 2);
			LCD.drawInt((int) prov.chassis.getMaxLinearSpeed(), 2, 3);
			LCD.drawInt((int)prov.chassis.getLinearSpeed(), 2, 4);
			LCD.drawString(String.valueOf(prov.distances[0]), 2, 5);
			LCD.drawInt((int) prov.cAngles[0], 2, 6);

	        LCD.refresh();
		}
		Porpoise.stopPID();
		states.stopOP();
		cals.stopOP();

		
	}


}