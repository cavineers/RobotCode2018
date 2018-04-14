package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class disableAutoPositionOverride extends Command{
	public disableAutoPositionOverride() {
		this.setRunWhenDisabled(true);
	}
	@Override
	protected void initialize() {
		Robot.isAutoPosOverridden = false;
	}
	@Override
	protected boolean isFinished() {
		return true;
	}

}
