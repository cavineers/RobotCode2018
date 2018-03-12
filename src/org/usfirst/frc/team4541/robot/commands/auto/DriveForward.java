package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveForward extends TimedCommand {
	double velocity;
	public DriveForward (double velocity, double timeout) {
		super(timeout);
		requires(Robot.drivetrain);
		this.velocity = velocity;
	}
	
	protected void initialize() {
		Robot.drivetrain.drive(velocity, 0);
	}
	protected void end() {
		Robot.drivetrain.drive(0, 0);
    }
	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
}
