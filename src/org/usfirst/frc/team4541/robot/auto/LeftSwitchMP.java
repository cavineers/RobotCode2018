package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.auto.DriveForward;
import org.usfirst.frc.team4541.robot.commands.auto.DrivePath;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorHome;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class LeftSwitchMP extends CommandGroup {
	public  LeftSwitchMP() {
		addSequential(new setIntakeContracted(true));
		addSequential(new ElevatorToHeight(ElevatorConstants.twoInches));
		addSequential(new TimedCommand(1));
		
    	addParallel(new ElevatorToHeight(ElevatorConstants.switchHeight)); // move up elevator
    	
    	addSequential(new DrivePath(PATHS.LEFT_SWITCH)); //drive to switch
    	
    	addSequential(new DriveForward(AutoConstants.driveForwardVel, AutoConstants.driveForwardTime)); //make sure that we're touching the wall by driving forward a bit
    	addParallel(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at speed while opening grabber
    	addSequential(new TimedCommand(3));
    	addParallel(new setIntakeSpeed(0)); 
    	addSequential(new DriveToPosAtAngle(21, 0));
    	//    	addSequential(new setIntakeContracted(false));
	}
}
