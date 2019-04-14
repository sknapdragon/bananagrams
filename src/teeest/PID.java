package teeest;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.robotics.SampleProvider;

public class PID extends Thread{
	private float target = 0, current, start = 0;
	private float kp = (float) 4.6, ki=(float)0.00, kd=0;
	private float error, lasterror, errorsum, slope, correction;
	private boolean on = true;
	private DataExchange DE;
	private SampleProvider compasspr;
	PID(DataExchange exc, SampleProvider cppr) {
		DE = exc;
		target = start;
		lasterror = 0;
		errorsum = 0;
		error = 0;
		compasspr = cppr;
	}

	public void run() {
		float[] cur = new float[10];
		compasspr.fetchSample(cur, 1);
		start = cur [1];
		while (on == true) {
		compasspr.fetchSample(cur, 1);

		runPID(cur[1]);
	}
	}
	
	public void stopPID(){
		on = false;
	}
	public float runPID(float cur) {
	current = cur;
	lasterror = error;
	error = target - current;
	errorsum += error;
	slope = error - lasterror;
	correction = kp*error + ki * errorsum + kd*slope;
	return correction;
	

	}
	

	public float getCorrection() {
		return correction;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public void setKp(float kp) {
		this.kp = kp;
	}

	public void setKi(float ki) {
		this.ki = ki;
	}

	public void setKd(float kd) {
		this.kd = kd;
	}

	public void setTarget(float tgt) {
		target = tgt;
	}
	
}

