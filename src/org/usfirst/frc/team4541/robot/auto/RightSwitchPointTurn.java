package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.auto.DriveForward;
import org.usfirst.frc.team4541.robot.commands.auto.DrivePath;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.auto.ZeroYaw;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightSwitchPointTurn extends CommandGroup {

    public  RightSwitchPointTurn() {
//    	addSequential(new ElevatorToHeight(ElevatorConstants.maxElevatorHeight / 2));
//    	addSequential(new DrivePath(PATHS.LEFT_SWITCH));
//    	addSequential(new EjectCube());
    	addSequential(new ZeroYaw());
    	addSequential(new setIntakeContracted(true)); 
    	addParallel(new ElevatorToHeight(ElevatorConstants.switchHeight)); // move up elevator
    	
    	addSequential(new DriveToPosAtAngle(3, 0)); //drive to switch
    	addSequential(new TurnToAngle(-90));
    	addSequential(new DriveToPosAtAngle(4, -90));
    	addSequential(new TurnToAngle(0));
    	addSequential(new DriveToPosAtAngle(5.5, 0));
    	
    	addSequential(new DriveForward(AutoConstants.driveForwardVel, AutoConstants.driveForwardTime)); //make sure that we're touching the wall by driving at half speed for half a second
    	addParallel(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at half speed while opening grabber
    	addSequential(new setIntakeContracted(false));
    	
    }
}
