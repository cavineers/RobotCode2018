package org.usfirst.frc.team4541.robot.commandGroups;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.DriveForward;
import org.usfirst.frc.team4541.robot.commands.DrivePath;
import org.usfirst.frc.team4541.robot.commands.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.commands.ElevatorToHeight;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;
import org.usfirst.frc.team4541.robot.commands.ToggleIntake;
import org.usfirst.frc.team4541.robot.commands.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightSwitchPointTurn extends CommandGroup {

    public  RightSwitchPointTurn() {
//    	addSequential(new ElevatorToHeight(ElevatorConstants.maxElevatorHeight / 2));
//    	addSequential(new DrivePath(PATHS.RIGHT_SWITCH));
//    	addSequential(new EjectCube());
    	addSequential(new setIntakeContracted(true));
    	addParallel(new ElevatorToHeight(ElevatorConstants.switchHeight)); // move up elevator
    	
    	addSequential(new DriveToPosAtAngle(3, 0)); //drive to switch
    	addSequential(new TurnToAngle(90));
    	addSequential(new DriveToPosAtAngle(4, 90));
    	addSequential(new TurnToAngle(0));
    	addSequential(new DriveToPosAtAngle(5.5, 0));
    	
    	addSequential(new DriveForward(0.5, 0.5)); //make sure that we're touching the wall by driving at half speed for half a second
    	addParallel(new setIntakeSpeed(-0.4)); //spin wheels at half speed while opening grabber
    	addSequential(new setIntakeContracted(false));
    }
}
