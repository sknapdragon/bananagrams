package teeest;

import lejos.robotics.chassis.Chassis;

public class Operation {
	Chassis chassis;
	int [] colorRGB;
	int irStrength,irDirection;
	int utsDistance;
	int compassReading;
	int PIDturn;
	
	int state;
	int OtherRobotState = 0;
	
	public Operation() {
		
	};
	
	public void runOperation() {
		if ( OtherRobotState == 2 ) // taking a shot
		{
			//set state to pushed up goalie.
		}
		switch ( state) {
		case 1:
			forwardOperation();
			break;
		case 10:
			forwardOperation();
		
		case 12:
			shoot();
	}


			
	}

	public void forwardOperation() {
		while(state == 1) {
		int linearSpeed = 500, polarDirection = 500, angVelocity = PIDturn;
		if (hasBall()) {
			state  = 12;
		}
		//chase at the ball
		//set the direction
		FootballBot.setDirection(irDirection, irStrength);
		chassis.setVelocity( linearSpeed, polarDirection , angVelocity);
		state = 1;
		}
	}
	private void shoot() {
		// given the parameters, attempt to make a shot.
		// can lead to three states, we score and restart, we miss and the ball is reset, or we lose the ball
		// while we have the ball, the goalie scoots up to prepare to either become the forward and shoot, or back up
		// we score ( This robot returns to center)
		// The ball is reset - > send a statechange to the other robot, prepare to go to the goal.
		turnAtGoal();
		while(hasBall()) {
			//drive at the goal
		}
	}


	public boolean hasBall() {
		//take rgb values and calculate wether or not we have the ball
		return true;
	}
	
	public void goalieOperation() {
		if (state == 2) {
			//goalie operation
			if ( OtherRobotState == 2);
		}
		// take the ultrasonic sensor's values, scoot towards the center, and realign with the goalie's box.
	}
	
	
	
	
	
	
	
	//calculation methods
	private void turnAtGoal() {
		// TODO Auto-generated method stub
		
	}

}
