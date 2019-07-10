package ForwardBot;

public class BallData {

	public class PolarCoords{
		int BallStr;
		int BallAngle;
	}
	public class CartCoords{
		int ballX;
		int ballY;
		public CartCoords(int bx, int by) {
			ballX = bx;
			ballY = by;
		}
	}
	public CartCoords convertToCartiesian(PolarCoords pcs) {
		int ballX = (int) (Math.cos(pcs.BallAngle + 90)*pcs.BallStr); 
		int ballY = (int) (Math.cos(pcs.BallAngle + 90)*pcs.BallStr); 
		
		return new CartCoords(ballX, ballY);
		
	}
	
	
	
}
