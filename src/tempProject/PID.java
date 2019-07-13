package tempProject;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class PID extends Thread{
	double target = 0;
	private float current, start = 0;
	private float kp = (float) 2, ki=(float)0.00, kd=1;
	private float error, lasterror, errorsum, slope, correction;
	private boolean on = true;
	private DataExchange DE;
	private float[] compassAngles;
	RelevantProviders prov;
	PID(DataExchange exc, float[] ca, RelevantProviders pro) {
		DE = exc;
		target = start;
		lasterror = 0;
		errorsum = 0;
		error = 0;
		compassAngles = ca;
		prov = pro;
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
	public void runPID(float cur) {
	current = cur;//lmao
	lasterror = error;
	error = (float) shift180err(target, current);
	errorsum += error;
	slope = error - lasterror;
	correction =kp*error + ki * errorsum + kd*slope;
	

	}
	public double distanceBetweenAlt(double x, double y) {

		 double   a = (x - y) % 360;
		    double b = (y - x) % 360;
		    if (a < b)
		    	return -a;
		    else return b;
	}
	
	public double shift180err(double target, double current) {
		double truecurrent = current;
		double truetarget = target;
		if (current < 0)
			truecurrent = current + 360;
		if (target < 0)
			truetarget = target +360;
		double a = truetarget - truecurrent;
		if ( a > 180) {
			a -=360;
		}
		else if (a < -180) {
			a += 360;
		}
		return a;
	}
	
    public double distanceBetween(double target, double current) { // partially borked
        double e = target - current;
        if (e > 180)
        e = e - 360;
        else if (e < -180);
        e += 360;
        return e;
    }
    public float getError() {
    	return error;
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
	
	public void setRelativeTarget(double tgtAngle) {
		target = start + tgtAngle;
	}
	
	public void restartTarget() {
		target = start;
	}
	public void incKP() {
		kp += .1;
	}
	public void incKI() {
		ki =(float) (ki+ .05);
	}
	public void incKD() {
		kd =(float) ((float)kd + .1);
	}

	public float getKp() {
		return kp;
	}

	public float getKi() {
		return ki;
	}

	public float getKd() {
		return kd;
	}
	public float getcurrent() {
		return current;
	}
	}

