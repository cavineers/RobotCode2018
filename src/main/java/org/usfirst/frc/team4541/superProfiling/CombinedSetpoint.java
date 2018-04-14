package org.usfirst.frc.team4541.superProfiling;

public class CombinedSetpoint {
	public boolean isLast;
	public int dt;
	
	public double rVel;
	public double rPos;
	public double rAccel;
	
	public double lVel;
	public double lPos;
	public double lAccel;
	
	public double heading;
	
	public CombinedSetpoint(boolean isLast, int dt, double lVel, double lPos, double lAccel, double rVel, double rPos, double rAccel, double heading) {
		this.isLast = isLast;
		this.dt = dt;
		
		this.lVel = lVel;
		this.lPos = lPos;
		this.lAccel = lAccel;
		
		this.rVel = rVel;
		this.rPos = rPos;
		this.rAccel = rAccel;
		
		this.heading = heading;
	}
	public CombinedSetpoint(Setpoint leftPoint, Setpoint rightPoint) {
		this.isLast = leftPoint.isLast || rightPoint.isLast;
		this.dt = leftPoint.dt; //Left and right *should* be same dt
		
		this.lVel   = leftPoint.velocity;
		this.lPos   = leftPoint.position;
		this.lAccel = leftPoint.accel;
		 
		this.rVel   = rightPoint.velocity;
		this.rPos   = rightPoint.position;
		this.rAccel = rightPoint.accel;
		
		this.heading = leftPoint.heading; //Left and right *should* be same heading
	}
	public Setpoint getRightSetpoint() {
		return new Setpoint(isLast, dt, heading, rPos, rVel, rAccel);
	}
	public Setpoint getLeftSetpoint() {
		return new Setpoint(isLast, dt, heading, lPos, lVel, lAccel);
	}
}
