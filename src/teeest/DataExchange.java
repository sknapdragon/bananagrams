package teeest;

public class DataExchange {
	private float currentangle;
	private float correction = 0;
	private float kp, ki, kd;
	private float target;
	
	
	public float getCurrentangle() {
		return currentangle;
	}
	public void setCurrentangle(float currentangle) {
		this.currentangle = currentangle;
	}
	public float getCorrection() {
		return correction;
	}
	public void setCorrection(float correction) {
		this.correction = correction;
	}
	public float getKp() {
		return kp;
	}
	public void setKp(float kp) {
		this.kp = kp;
	}
	public float getKi() {
		return ki;
	}
	public void setKi(float ki) {
		this.ki = ki;
	}
	public float getKd() {
		return kd;
	}
	public void setKd(float kd) {
		this.kd = kd;
	}
	public float getTarget() {
		return target;
	}
	public void setTarget(float target) {
		this.target = target;
	}
	
}
