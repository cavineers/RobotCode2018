package org.usfirst.frc.team4541.robot.commands.control;

import org.usfirst.frc.team4541.robot.commands.control.BriefRumble.ControllerSide;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class rightLeftRumble extends CommandGroup {
	public rightLeftRumble() {
		addSequential(new BriefRumble(0.1, ControllerSide.RIGHT));
		addSequential(new BriefRumble(0.1, ControllerSide.LEFT));
	}
}
