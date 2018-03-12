package org.usfirst.frc.team4541.robot.commands.elevator;

import org.usfirst.frc.team4541.robot.ElevatorConstants;

import org.usfirst.frc.team4541.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ElevatorHome extends Command {

	int step = 0;

	public ElevatorHome() {
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getPIDVel().reset();
		Robot.elevator.getPIDMotorOutput().reset();
		Robot.elevator.getElevatorMotor().setNeutralMode(NeutralMode.Brake);
		Robot.elevator.getElevatorMotor().set(ControlMode.PercentOutput, .7);
		step = 1;
		setTimeout(5);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		switch (step) {
		case 1:
			if (Robot.elevator.getElevatorMotor().getSelectedSensorPosition(0) >= ElevatorConstants.twoInches) {
				Robot.elevator.getElevatorMotor().set(ControlMode.PercentOutput, -.1);
				step = 2;
			}
			break;

		case 2:
			if (!Robot.elevator.limitSwitch.get()) {
				Robot.elevator.getElevatorMotor().setSelectedSensorPosition(0, 0, 0);
				Robot.elevator.getPIDVel().setSetpoint(0);
				Robot.elevator.getElevatorMotor().stopMotor();
				step = 3;
			}
			break;

		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return step == 3 || isTimedOut();

	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.elevatorMotor.stopMotor();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
