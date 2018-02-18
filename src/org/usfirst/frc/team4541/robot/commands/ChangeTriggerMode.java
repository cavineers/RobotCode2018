package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeTriggerMode extends Command {
	private TRIG_MODE newMode;
	public ChangeTriggerMode(TRIG_MODE newMode) {
		this.newMode = newMode;
	}
	protected void initialize() {
		Robot.oi.currentTriggerSetting = newMode;
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
