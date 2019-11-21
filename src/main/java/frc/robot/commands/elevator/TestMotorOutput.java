package frc.robot.commands.elevator;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestMotorOutput extends Command {

	double desiredVelocity = 500;

	public TestMotorOutput() {

		requires(Robot.elevator);
		SmartDashboard.putNumber("Desired Velocity (TestMotorOutput)", desiredVelocity);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getPIDMotorOutput().enable();
		Robot.elevator.getPIDVel().disable();
		Robot.elevator.getPIDMotorOutput().setSetpoint(desiredVelocity);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		desiredVelocity = SmartDashboard.getNumber("Desired Velocity (TestMotorOutput)", -1);
		Robot.elevator.getPIDMotorOutput().setSetpoint(desiredVelocity);

		SmartDashboard.putNumber("Current Velocity (TestMotorOutput)",
				Robot.elevator.getElevatorMotor().getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Velocity Error (TestMotorOutput)", Robot.elevator.getPIDMotorOutput().getError());

		SmartDashboard.putNumber("Current motot output (TestMotorOutput)", Robot.elevator.getPIDMotorOutput().get());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.getElevatorMotor().stopMotor();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
