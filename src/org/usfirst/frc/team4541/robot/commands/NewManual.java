package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class NewManual extends Command {
	double elevPos;
	double upTrig;
	double downTrig;
	boolean changePos = false;

	public NewManual() {

		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getPIDMotorOutput().enable();
		Robot.elevator.getPIDVel().enable();
		elevPos = Robot.elevator.getElevatorPos();
		SmartDashboard.putNumber("Elevator Height (NewManual)", elevPos);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.oi.currentTriggerSetting != TRIG_MODE.ELEVATOR) {
			return;
		}
		elevPos = Robot.elevator.getElevatorPos();
		upTrig = Robot.oi.getJoystick().getRawAxis(3);
		downTrig = Robot.oi.getJoystick().getRawAxis(2);
		SmartDashboard.putNumber("upTrig (NewManual)", upTrig);
		SmartDashboard.putNumber("ELEVATOR POSITION (NewManual)", Robot.elevator.getElevatorPos());
		SmartDashboard.putNumber("downTrig (NewManual)", downTrig);

		if (upTrig < 0.05 && downTrig < 0.05 || upTrig > 0.05 && downTrig > 0.05) {
			if (changePos) {
				Robot.elevator.getPIDVel().setSetpoint(elevPos);
				changePos = false;
				Robot.elevator.setTriggerValue(9999);
			}
		}

		if (upTrig > 0.05 && downTrig < 0.05) {
			Robot.elevator.setTriggerValue(ElevatorConstants.maxSpeed * upTrig);
			changePos = true;
			Robot.elevator.getPIDVel().setSetpoint(ElevatorConstants.maxElevatorHeight);
		}
		if (downTrig > 0.05 && upTrig < 0.05) {
			Robot.elevator.setTriggerValue(-1 * (ElevatorConstants.maxSpeed * downTrig));
			changePos = true;
			Robot.elevator.getPIDVel().setSetpoint(ElevatorConstants.minElevatorHeight);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.getPIDMotorOutput().disable();
		Robot.elevator.getPIDVel().disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
