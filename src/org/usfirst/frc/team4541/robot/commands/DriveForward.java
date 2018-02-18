package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForward extends Command{
	public DriveForward (double timeout) {
		this.setTimeout(timeout);
		requires(Robot.drivetrain);
	}
	
	protected void initialize() {
		Robot.drivetrain.drive(0.3, 0);
	}
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
}
