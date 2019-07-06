package datatests;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.HiTechnicColorSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.HiTechnicIRSeekerV2;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class ForwardDataTest {
public static void main(String[] args) {
	WifiClient WIFICL;
	WIFICL = new WifiClient();
	int target = 0;
	RegulatedMotor ma = new EV3MediumRegulatedMotor(MotorPort.A);
	RegulatedMotor mb = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor mc = new EV3LargeRegulatedMotor(MotorPort.C);
	RegulatedMotor md = new EV3MediumRegulatedMotor(MotorPort.D);

	Wheel wheela = WheeledChassis.modelHolonomicWheel(mc, 61).polarPosition(30, 70).gearRatio(1);
	Wheel wheelb = WheeledChassis.modelHolonomicWheel(mb, 61).polarPosition(150, 70).gearRatio(-1); // reversed wheel b
	Wheel wheelc = WheeledChassis.modelHolonomicWheel(ma, 61).polarPosition(270, 70).gearRatio(1);
	Chassis chassis = new WheeledChassis(new Wheel[] { wheela, wheelb, wheelc }, WheeledChassis.TYPE_HOLONOMIC);
	Port s1 = LocalEV3.get().getPort("S1");
	Port s2 = LocalEV3.get().getPort("S2");
	Port s3 = LocalEV3.get().getPort("S3");
	Port s4 = LocalEV3.get().getPort("S4");

	SensorModes compassSensor = new HiTechnicCompass(s1);
	SensorModes colorSensor = new HiTechnicColorSensor(s2);
	SensorModes irSeeker = new HiTechnicIRSeekerV2(s3);
	SensorModes UltraSensor = new EV3UltrasonicSensor(s4);

	while (Button.DOWN.isUp()) {
		target = WIFICL.getTarget();
		LCD.drawInt(target, 0, 0);
	}
	

}
}
