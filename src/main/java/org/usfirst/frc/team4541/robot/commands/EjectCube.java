package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class EjectCube extends TimedCommand {

    public EjectCube() { //Spins intake motors backward for 1 second to eject the cube
        super(1); // runs command for 1 second
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.setIntakeSpeed(-0.4);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Called once after timeout
    protected void end() {
    	Robot.intake.setIntakeSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
