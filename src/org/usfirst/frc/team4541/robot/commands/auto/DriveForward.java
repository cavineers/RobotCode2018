package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForward extends Command{
	double velocity;
	public DriveForward (double velocity, double timeout) {
		this.setTimeout(timeout);
		requires(Robot.drivetrain);
		this.velocity = velocity;
	}
	
	protected void initialize() {
		Robot.drivetrain.drive(velocity, 0);
	}
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
}
