package org.usfirst.frc.team4541.robot.commands.elevator;

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
	boolean isLastUp    = false;
	double velI;

	public NewManual() {
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		elevPos = Robot.elevator.getElevatorPos();
		SmartDashboard.putNumber("Elevator Height (NewManual)", elevPos);
		velI = Robot.elevator.getPIDVel().getI();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.elevator.updatePIDVals();
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
				changePos = false;
				Robot.elevator.setTriggerValue(9999);
				if(isLastUp) {
					Robot.elevator.getPIDVel().setSetpoint(elevPos + 300);
				} else {
					Robot.elevator.getPIDVel().setSetpoint(elevPos - 300);
				}
			}
		}

		if (upTrig > 0.05 && downTrig < 0.05) {
			isLastUp = true;
			Robot.elevator.setTriggerValue(ElevatorConstants.maxSpeed * Math.pow(upTrig, 2));
			changePos = true;
			Robot.elevator.getPIDVel().setSetpoint(ElevatorConstants.maxElevatorHeight);
		}
		if (downTrig > 0.05 && upTrig < 0.05) {
			isLastUp = false;
			Robot.elevator.setTriggerValue(-1 * (ElevatorConstants.maxSpeed * Math.pow(downTrig, 2)));
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
