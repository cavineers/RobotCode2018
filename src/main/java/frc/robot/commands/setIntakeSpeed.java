package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class setIntakeSpeed extends Command {
	double speed;
    public setIntakeSpeed(double s) { //positive = intake, negative = eject
    	requires(Robot.intake);
    	speed = s;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.setIntakeSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}
