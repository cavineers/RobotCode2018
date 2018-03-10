package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class BriefRumble extends TimedCommand {

	public BriefRumble() {
		super(0.25);
		Robot.oi.getJoystick().setRumble(RumbleType.kLeftRumble, 1);
		Robot.oi.getJoystick().setRumble(RumbleType.kRightRumble, 1);
	}
	
	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	protected void end() {
		Robot.oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0);
		Robot.oi.getJoystick().setRumble(RumbleType.kRightRumble, 0);
    }
}
