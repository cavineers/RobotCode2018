package frc.robot.commands.elevator;

import frc.robot.OI.TRIG_MODE;
import frc.robot.Robot;
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
		Robot.elevator.getPIDVel().setSetpoint(targetHeight);
		//Robot.elevator.updatePIDVals();
		if (!Robot.elevator.getPIDVel().isEnabled() || !Robot.elevator.getPIDMotorOutput().isEnabled()) {
			Robot.elevator.getPIDMotorOutput().enable();
			Robot.elevator.getPIDVel().enable();
		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		
	}

	protected void interrupted() {
		end();
	}

	protected boolean isFinished() {
		double upTrig = Robot.oi.getJoystick().getRawAxis(3);
		double downTrig = Robot.oi.getJoystick().getRawAxis(2);
		return Robot.elevator.getPIDVel().onTarget() || ((upTrig > 0.05 || downTrig > 0.05) && Robot.oi.currentTriggerSetting == TRIG_MODE.ELEVATOR);
//		return true;
	}

	protected void end() {
	}

}
