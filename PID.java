package teeest;


public class PID{
	private float target = 0, current, start = 0;
	private float kp = 1, ki=1, kd=1;
	private float error, lasterror, errorsum, slope;
	private boolean on = true;
	
	public PID(float st, float cr) {
		start = st;
		target = start;
		current = cr;
		lasterror = 0;
		errorsum = 0;
		error = 0;
	}

	public float runPID() {
		
	lasterror = error;
	error = target - current;
	errorsum += error;
	slope = error - lasterror;
	float correction = kp*error + ki * errorsum + kd*slope;
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

