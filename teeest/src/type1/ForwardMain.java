package type1;

import java.util.Arrays;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.chassis.Chassis;
import type1.PID;

public class ForwardMain {
	public static RelevantProviders prov;
	public static DataExchange Exc;

	public static void main(String[] args) {
		prov = new RelevantProviders();
		Exc = new DataExchange();
		PID Porpoise = new PID(Exc, prov.cAngles);
		updateSensors UPSENS = new updateSensors(prov);
		RS RunTheBot = new RS(prov.chassis, prov, Porpoise);
		UPSENS.start();
		Button.waitForAnyPress();
		Porpoise.start();
		Button.waitForAnyPress();

		RunTheBot.start();

		
		while (!Button.ESCAPE.isDown()) {


		// PID test used in day 1	prov.chassis.setVelocity(0, 0, Porpoise.getCorrection());
			LCD.drawInt((int) prov.irAngles [0], 2, 0);
			LCD.drawInt((int) prov.irStr [2], 2, 1);
			LCD.drawInt(prov.avgStr(), 6, 1);
			LCD.drawInt((int) (255*prov.colorReadings[2]), 2, 2);
			LCD.drawInt((int) prov.chassis.getMaxLinearSpeed(), 2, 3);
			LCD.drawInt((int) Porpoise.getCorrection(), 2, 4);
			LCD.drawInt((int) prov.cAngles[0], 2, 5);

	        LCD.refresh();
		}
		Porpoise.stopPID();
		UPSENS.stopOP();
		RunTheBot.stopOP();
		
	}


}
