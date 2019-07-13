package tempProject;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class testPID {
	// These providers are refreshed to be pulled from
	// the data members are public because I am lazy\
	public static void main(String[] args) {
		
	
	RelevantProviders prov;
	DataExchange Exc;
	prov = new RelevantProviders();
	prov.updateCompass();
	Exc = new DataExchange(); // exchanges data properly between the pid and the Operation thread
	PID Porpoise = new PID(Exc, prov.cAngles, prov); // PID which realigns robo's angle\
	UpdateProviders upd = new UpdateProviders(prov);


	Button.waitForAnyPress();
	upd.start();
	Porpoise.start();
	Button.waitForAnyPress();
	


	while (!Button.ESCAPE.isDown()) {

		// PID test used in day 1 prov.chassis.setVelocity(0, 0,
		// Porpoise.getCorrection());
		LCD.drawString(""+Porpoise.getKp(), 1, 1);
		LCD.drawString(""+Porpoise.getKi(), 1, 2);
		LCD.drawString(""+Porpoise.getKd(), 1, 3);
if (Button.LEFT.isDown())
	Porpoise.incKP();
if (Button.ENTER.isDown())
	Porpoise.incKI();
if (Button.RIGHT.isDown())
	Porpoise.incKD();
		LCD.drawInt((int) prov.cAngles[0], 1, 6);
		LCD.drawString(""+Porpoise.getError(), 1, 4);
		LCD.drawInt((int) Porpoise.getCorrection(), 1, 7);
		LCD.drawString(""+Porpoise.distanceBetweenAlt( Porpoise.target, Porpoise.getcurrent()), 1, 5);
		LCD.refresh();
		prov.chassis.setVelocity(0, 0, Porpoise.getCorrection());
	}
	Porpoise.stopPID();
	upd.stopOp();
	}
}
