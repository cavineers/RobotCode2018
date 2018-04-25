package org.usfirst.frc.team4541.robot.commands.elevator;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualMoveElevator extends Command {
	double elevPos;
	double upTrig;
	double downTrig;
	boolean changePos = false;

	public ManualMoveElevator() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (!Robot.elevator.getPIDVel().isEnabled() || !Robot.elevator.getPIDMotorOutput().isEnabled()) {
			elevPos = Robot.elevator.getElevatorPos();
			Robot.elevator.getPIDVel().setSetpoint(elevPos);
			Robot.elevator.getPIDMotorOutput().setSetpoint(0);
			Robot.elevator.getPIDMotorOutput().enable();
			Robot.elevator.getPIDVel().enable();

		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		upTrig = Robot.oi.getJoystick().getRawAxis(3);
		downTrig = Robot.oi.getJoystick().getRawAxis(2);
		if (upTrig > 0.05 && downTrig < 0.05 && Robot.oi.currentTriggerSetting == TRIG_MODE.ELEVATOR) {
			Robot.elevator.setTriggerValue(ElevatorConstants.maxSpeed * Math.pow(upTrig, 2));
			Robot.elevator.getPIDVel().setSetpoint(ElevatorConstants.maxElevatorHeight);
			// Robot.elevator.updatePIDVals();
		}

		else if (downTrig > 0.05 && upTrig < 0.05 && Robot.oi.currentTriggerSetting == TRIG_MODE.ELEVATOR) {
			Robot.elevator.setTriggerValue(-1 * (ElevatorConstants.maxSpeed * Math.pow(downTrig, 2)));
			Robot.elevator.getPIDVel().setSetpoint(ElevatorConstants.minElevatorHeight);
			// Robot.elevator.updatePIDVals();

		}

		else {
			Robot.elevator.setTriggerValue(0);

			if (!Robot.elevator.getPIDVel().onTarget()) {
				Robot.elevator.getPIDVel().setSetpoint(Robot.elevator.getElevatorPos());
				//Robot.elevator.updatePIDVals();
				System.out.println("not on target");
			}
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.elevator.setTriggerValue(9999);
	}
}
