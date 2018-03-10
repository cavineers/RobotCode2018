package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleIntake extends Command {

	public ToggleIntake() {
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
    	if (Robot.oi.currentTriggerSetting != TRIG_MODE.INTAKE) {
	    	if (Math.abs(Robot.intake.intakeMotor1.get()) > 0.25) {
	    		Robot.intake.setIntakeSpeed(0);
	    		Robot.intake.isCurrentLimited = false;
	    	} else {
	    		Robot.intake.setIntakeSpeed(0.7);
	    		Robot.intake.isCurrentLimited = true;
	    	}
    	} else {
    		
    	}
    }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}
}
