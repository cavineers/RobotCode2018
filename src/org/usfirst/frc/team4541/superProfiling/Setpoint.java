package org.usfirst.frc.team4541.superProfiling;

public class Setpoint {
	public boolean isLast;
	public double velocity;
	public double position;
	public double accel;
	public int dt;
	public double heading;
	public Setpoint(boolean isLast, int dt, double heading, double position,  double velocity, double accel) {
		this.isLast = isLast;
		this.dt = dt;
		this.heading = heading;
		this.velocity = velocity;
		this.position = position;
		this.accel = accel;
	}
}
