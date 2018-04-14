package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OverrideAutoPosition extends Command{
	public OverrideAutoPosition() {
		this.setRunWhenDisabled(true);
	}
	@Override
	protected void initialize() {
		Robot.isAutoPosOverridden = true;
		Robot.overriddenRobotPos = Robot.posChooser.getSelected();
	}
	@Override
	protected boolean isFinished() {
		return true;
	}

}
