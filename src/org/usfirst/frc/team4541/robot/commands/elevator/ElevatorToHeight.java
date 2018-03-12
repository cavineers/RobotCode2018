package org.usfirst.frc.team4541.robot.commands.elevator;

import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
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
	}

	protected void initialize() {
		Robot.elevator.getPIDVel().enable();
		Robot.elevator.getPIDMotorOutput().enable();
		Robot.elevator.getPIDVel().setSetpoint(targetHeight);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.elevator.getPIDVel().setSetpoint(this.targetHeight);
		SmartDashboard.putNumber("Current Height (ElevatorToHeight)", Robot.elevator.getElevatorPos());
	}

	protected void interrupted() {
		end();
	}

	protected boolean isFinished() {
		double upTrig = Robot.oi.getJoystick().getRawAxis(3);
		double downTrig = Robot.oi.getJoystick().getRawAxis(2);
		return Robot.elevator.getPIDVel().onTarget() || ((upTrig > 0.05 || downTrig > 0.05) && Robot.oi.currentTriggerSetting == TRIG_MODE.ELEVATOR);

	}

	protected void end() {
		Robot.elevator.getPIDVel().reset();
		Robot.elevator.getPIDMotorOutput().reset();
	}

}
