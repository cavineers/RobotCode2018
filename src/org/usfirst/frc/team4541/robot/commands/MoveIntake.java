package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.robot.OI;
import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveIntake extends Command {
	public MoveIntake() {
		requires(Robot.intake);
	}
	protected void execute() {
		if (Robot.oi.currentTriggerSetting != OI.TRIG_MODE.INTAKE) {
			return;
		}
		double upTrig = Robot.oi.getJoystick().getRawAxis(3);
		double downTrig = Robot.oi.getJoystick().getRawAxis(2);
		if (upTrig <= 0.05 && downTrig <= 0.05 || upTrig > 0.05 && downTrig > 0.05) {
			// both or neither triggers pressed, do nothing
			Robot.intake.setIntakeSpeed(0);
		} else if (upTrig > 0.05) {
			//Move up
			Robot.intake.setIntakeSpeed(upTrig);

		} else if (downTrig > 0.05) {
			//Move down
			Robot.intake.setIntakeSpeed(downTrig);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
