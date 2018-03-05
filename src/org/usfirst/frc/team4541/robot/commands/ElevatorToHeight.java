package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ElevatorToHeight extends Command {
	double targetHeight;

	public ElevatorToHeight(double theight) {
		requires(Robot.elevator);
		this.targetHeight = theight;

		SmartDashboard.putNumber("Target Height (ElevatorToHeight)", targetHeight);
	}

	protected void initialize() {
		Robot.elevator.getPIDVel().enable();
		Robot.elevator.getPIDMotorOutput().enable();
		Robot.elevator.getPIDVel().setSetpoint(targetHeight);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double elevPos = Robot.elevator.getElevatorPos();
		targetHeight = SmartDashboard.getNumber("Target Height (ElevatorToHeight)", -1);
		Robot.elevator.getPIDVel().setSetpoint(targetHeight);

		SmartDashboard.putNumber("Current Height (ElevatorToHeight)",
				Robot.elevator.getElevatorMotor().getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Height Error (ElevatorToHeight)", Robot.elevator.getPIDVel().getError());

	}

	protected void interrupted() {
		end();
	}

	protected boolean isFinished() {
		double upTrig = Robot.oi.getJoystick().getRawAxis(3);
		double downTrig = Robot.oi.getJoystick().getRawAxis(2);
		return Robot.elevator.getPIDVel().onTarget() || upTrig > 0.05 || downTrig > 0.05;

	}

	protected void end() {
		Robot.elevator.getPIDVel().disable();
		Robot.elevator.getPIDMotorOutput().disable();
	}

}
