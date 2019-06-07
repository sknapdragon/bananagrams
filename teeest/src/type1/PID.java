package type1;

import lejos.robotics.SampleProvider;

public class PID extends Thread{
	private float target = 0, current, start = 0;
	private float kp = (float) 3, ki=(float)0.000, kd=3;
	private float error, lasterror, errorsum, slope, correction;
	private boolean on = true;
	private DataExchange DE;
	private float[] compassAngles;
	PID(DataExchange exc, float[] ca) {
		DE = exc;
		target = start;
		lasterror = 0;
		errorsum = 0;
		error = 0;
		compassAngles = ca;
	}

	public void run() {
		start = compassAngles[0];
		target = start;
		while (on) {
		

		runPID(compassAngles[0]);
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
