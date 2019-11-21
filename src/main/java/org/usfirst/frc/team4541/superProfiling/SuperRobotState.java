package org.usfirst.frc.team4541.superProfiling;

public class SuperRobotState {
	public double rVel;
	public double rPos;
	
	public double lVel;
	public double lPos;
	
	public double heading;
	
	public SuperRobotState(double lVel, double lPos, double rVel, double rPos, double heading) {
		this.lVel = lVel;
		this.lPos = lPos;
		
		this.rVel = rVel;
		this.rPos = rPos;
		
		this.heading = heading;
	}
	
	public SuperRobotSide getRightSide() {
		return new SuperRobotSide(rVel, rPos);
	}
	public SuperRobotSide getLeftSide() {
		return new SuperRobotSide(lVel, lPos);
	}
	
}
