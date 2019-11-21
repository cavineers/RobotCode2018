package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class setIntakeContracted extends Command {
	public boolean doContract = false;
    public setIntakeContracted(boolean doContract) {
    	this.doContract = doContract;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.setSolenoidOpen(doContract);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
