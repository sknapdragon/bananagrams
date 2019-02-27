package teeest;

import lejos.robotics.SampleProvider;

public class PID {
	protected float lasterror = 0; float error; float compstart;float currentangle;
	public PID (float last, float err, float cstart, float cangle) {
		lasterror = last;
		error = err;
		compstart = cstart;
		currentangle = cangle;
	}
	protected static float angleCorrect(SampleProvider compdistancePro,float [] compassangles, float start , float previouscorrection) {

		float kp,ki,kd;
		float errorsum = 0; float slope; float error=0;float lasterror;
		while(true){
			compdistancePro.fetchSample(compassangles, 0);
		kp = 10;
		//remember why writing this as a method doesn't 'work' rn
		ki = 10;
		kp = 10;

		lasterror = error;
		error = start - compassangles[0];
		errorsum += error;
		slope = error - lasterror;
		float correction = kp*error + ki * errorsum + kp*slope;
		return correction;
		}
	}

}
