package ForwardBot;

import java.util.ArrayList;

import ForwardBot.BallData.CartCoords;
import ForwardBot.BallData.CartVelocity;
import ForwardBot.BallData.PolarCoords;

public class CalcCal extends Thread {
	ArrayList<BallData.CartCoords> lastXVals = new ArrayList<BallData.CartCoords>();
	ArrayList<BallData.PolarCoords> i2lastXVals = new ArrayList<BallData.PolarCoords>();
	boolean on = true;
	int maxValuesPerCycle = 1000;
	RelevantProviders prov;

	public CalcCal(ArrayList<CartCoords> lastXVals, int maxValuesPerCycle, RelevantProviders pro) {
		this.lastXVals = lastXVals;
		this.maxValuesPerCycle = maxValuesPerCycle;
		prov = pro;
	}

	public CalcCal(int maxValuesPerCycle,RelevantProviders pro) {
		this.maxValuesPerCycle = maxValuesPerCycle;
		prov = pro;
	}

	public void run() {
		while (on) {
			prov.updateIrAng();
			prov.updateIrStr();
			addCoords(lastXVals, new PolarCoords((int) prov.irAngles[0], prov.avgStr()));
		}
	}
	public void stopOP (){
		on = false;
	}

	public void addCoords(ArrayList<BallData.CartCoords> gen, BallData.PolarCoords polarcoord) {
		if (gen.size() < maxValuesPerCycle) {
			gen.add(BallData.convertToCartiesian(polarcoord));
		} else {
			gen.remove(0);
			gen.add(BallData.convertToCartiesian(polarcoord));
		}
	}
	

	public double estSpeed() {
		int x1 = lastXVals.get(lastXVals.size()).ballX;
		int y1 = lastXVals.get(lastXVals.size()).ballY;
		int x2 = lastXVals.get(0).ballX;
		int y2 = lastXVals.get(0).ballY;
		int size = lastXVals.size();
		double distance = Math.hypot(x1-x2, y1-y2);
		return distance/size;
	}
	public int estDirection() {
		int x1 = lastXVals.get(lastXVals.size()).ballX;
		int y1 = lastXVals.get(lastXVals.size()).ballY;
		int x2 = lastXVals.get(0).ballX;
		int y2 = lastXVals.get(0).ballY;
		return (int) (180.000/Math.PI * Math.atan2(y2-y1,x2-x1)); //Arctangent of displacement calculates an angle
	}
	public double estSpeedInstantously() {
		int x1 = lastXVals.get(lastXVals.size()).ballX;
		int y1 = lastXVals.get(lastXVals.size()).ballY;
		int x2 = lastXVals.get(0).ballX;
		int y2 = lastXVals.get(0).ballY;
		int size = lastXVals.size();
		return (y2-y1/x2-x1);
	}
	public int estDirectionInstantously() {
		int x1 = lastXVals.get(lastXVals.size()).ballX;
		int y1 = lastXVals.get(lastXVals.size()).ballY;
		int x2 = lastXVals.get(0).ballX;
		int y2 = lastXVals.get(0).ballY;
		return (int) (180.000/Math.PI * Math.atan2(y2-y1,x2-x1)); //Arctangent of displacement calculates an angle
	}
}
