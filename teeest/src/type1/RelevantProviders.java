package type1;

import CustomSensor.HiTechnicIRSeekerV2CustomStr;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.HiTechnicColorSensor;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class RelevantProviders {
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
	SensorModes irSeeker = new HiTechnicIRSeekerV2CustomStr(s3);
	SensorModes UltraSensor = new EV3UltrasonicSensor(s4);

	SampleProvider CmAngleProvider = compassSensor.getMode("Angle");
	SampleProvider colorPro = colorSensor.getMode("RGB");
	SampleProvider irStrPro = irSeeker.getMode("ModulatedMiddleStrength");
	SampleProvider irAnglePro = irSeeker.getMode("Modulated");
//	SampleProvider distancePro = UltraSensor.getMode("Distance");

	// the ARRAYS
	float[] irAngles = new float[irAnglePro.sampleSize()];
	float[] colorReadings = new float[colorPro.sampleSize()];
//	float[] distances = new float[distancePro.sampleSize()];
	float[] cAngles = new float[CmAngleProvider.sampleSize()];
	float[] irStr = new float[irStrPro.sampleSize()];
	
	public void updateAllProviders() {
		irAnglePro.fetchSample(irAngles, 0);
		colorPro.fetchSample(colorReadings, 0);
//		distancePro.fetchSample(distances, 0);
		CmAngleProvider.fetchSample(cAngles, 0);
		irStrPro.fetchSample(irStr, 0);
	}
	public void updateIRProviders() {
		irAnglePro.fetchSample(irAngles, 0);
		irStrPro.fetchSample(irStr, 0);
	}

}
