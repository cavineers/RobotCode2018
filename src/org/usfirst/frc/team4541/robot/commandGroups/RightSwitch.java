package org.usfirst.frc.team4541.robot.commandGroups;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.commands.DrivePath;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.commands.PIDMoveElevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightSwitch extends CommandGroup {

    public  RightSwitch() {
    	addSequential(new PIDMoveElevator(Constants.maxElevatorHeight / 2));
    	addSequential(new DrivePath(PATHS.RIGHT_SWITCH));
    	addSequential(new EjectCube());
    }
}
