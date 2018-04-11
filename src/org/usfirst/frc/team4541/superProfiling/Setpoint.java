package org.usfirst.frc.team4541.superProfiling;

public class Setpoint {
	public boolean isLast;
	public double velocity;
	public double position;
	public double accel;
	
	public Setpoint(boolean isLast, double position,  double velocity, double accel) {
		this.isLast = isLast;
		this.velocity = velocity;
		this.position = position;
		this.accel = accel;
	}
}
