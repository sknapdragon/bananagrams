package teeest;

import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class PIDthread extends Thread {
	SampleProvider compdistancePro;
	float start;
	float[] compassangles;
	public PIDthread(SampleProvider s, float st, float[] ca) {
		compdistancePro = s;
		start = st;
		compassangles = ca;
	}

	public void run() {
	
	float kp,ki,kd;
	float errorsum = 0; float slope; float error=0;float lasterror;
	while(true){
		compdistancePro.fetchSample(compassangles, 0);
	kp = 10;
	//remember why writing this as a method doesn't 'work' rn
	ki = 10;
	kd = 10;

	lasterror = error;
	error = start - compassangles[0];
	errorsum += error;
	slope = error - lasterror;
	float correction = kp*error + ki * errorsum + kd*slope;
	}
}
}
	
