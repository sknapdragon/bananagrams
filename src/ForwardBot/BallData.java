package ForwardBot;

import java.util.ArrayList;

public class BallData {

	public static class PolarCoords{
		public PolarCoords(int ballStr, int ballAngle) {
			BallStr = ballStr;
			BallAngle = ballAngle;
		}
		int BallStr;
		int BallAngle;
	}
	public static class CartCoords{
		int ballX;
		int ballY;
		
		public CartCoords(int bx, int by) {
			ballX = bx;
			ballY = by;
		}
	}
	
	public static class CartVelocity {
		public double getSpeed() {
			return speed;
		}

		public void setSpeed(double speed) {
			this.speed = speed;
		}

		public double getVelDirection() {
			return velDirection;
		}

		public void setVelDirection(double velDirection) {
			this.velDirection = velDirection;
		}
		double speed;
		double velDirection;
		public CartVelocity(double speed2, double direction) {
			speed = speed2;
			velDirection = direction;
		}
		
		public CartVelocity addVelocities(CartVelocity velA) {
			double speedAX = velA.speed * Math.cos(velA.velDirection);
			double speedAY = velA.speed * Math.sin(velA.velDirection);
			double speedBX = speed * Math.cos(velDirection);
			double speedBY = speed * Math.sin(velDirection);
			double sumspdX = speedAX + speedBX;
			double sumspdY = speedAY + speedBY;


			return 			genVelVectors(sumspdX, sumspdY);
			
		}
		public CartVelocity genVelVectors(double speedX , double speedY) {
			double speed = Math.hypot(speedX, speedY);
			double direction = Math.atan2(speedY, speedX) * 180/Math.PI;
			return new CartVelocity(speed, direction);
			
		}
		public CartVelocity calcVelocity(ArrayList<CartCoords> coordnates) {
			
			
			return null;
			
		}
		public static double estSpeed(ArrayList<CartCoords> coordnates) {
			int x1 = coordnates.get(coordnates.size()).ballX;
			int y1 = coordnates.get(coordnates.size()).ballY;
			int x2 = coordnates.get(0).ballX;
			int y2 = coordnates.get(0).ballY;
			int size = coordnates.size();
			double distance = Math.hypot(x1-x2, y1-y2);
			return distance/size;
		}
		public static int estDirection(ArrayList<CartCoords> coordnates) {
			int x1 = coordnates.get(coordnates.size()).ballX;
			int y1 = coordnates.get(coordnates.size()).ballY;
			int x2 = coordnates.get(0).ballX;
			int y2 = coordnates.get(0).ballY;
			return (int) (180.000/Math.PI * Math.atan2(y2-y1,x2-x1)); //Arctangent of displacement calculates an angle
		}
		public static double estSpeedInstantously(ArrayList<CartCoords> coordnates) {
			int x1 = coordnates.get(coordnates.size()).ballX;
			int y1 = coordnates.get(coordnates.size()).ballY;
			int x2 = coordnates.get(0).ballX;
			int y2 = coordnates.get(0).ballY;
			int size = coordnates.size();
			return (y2-y1/x2-x1);
		}
		public static int estDirectionInstantously(ArrayList<CartCoords> coordnates) {
			int x1 = coordnates.get(coordnates.size()).ballX;
			int y1 = coordnates.get(coordnates.size()).ballY;
			int x2 = coordnates.get(0).ballX;
			int y2 = coordnates.get(0).ballY;
			return (int) (180.000/Math.PI * Math.atan2(y2-y1,x2-x1)); //Arctangent of displacement calculates an angle
		}

	}

	
	public static BallData.CartCoords convertToCartiesian(BallData.PolarCoords pcs) {
		int newballX = (int) (Math.cos(pcs.BallAngle + 90)*pcs.BallStr); 
		int newballY = (int) (Math.sin(pcs.BallAngle + 90)*pcs.BallStr); 
		
		return new BallData.CartCoords(newballX, newballY);
	}
	
	
	
}
