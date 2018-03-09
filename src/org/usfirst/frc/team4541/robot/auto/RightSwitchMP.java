package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.DriveForward;
import org.usfirst.frc.team4541.robot.commands.DrivePath;
import org.usfirst.frc.team4541.robot.commands.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.ElevatorToHeight;
import org.usfirst.frc.team4541.robot.commands.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightSwitchMP extends CommandGroup {
	public  RightSwitchMP() {
		addSequential(new setIntakeContracted(true));
    	addParallel(new ElevatorToHeight(ElevatorConstants.switchHeight)); // move up elevator
    	
    	addSequential(new DrivePath(PATHS.RIGHT_SWITCH)); //drive to switch
    	
    	addSequential(new DriveForward(AutoConstants.driveForwardVel, AutoConstants.driveForwardTime)); //make sure that we're touching the wall by driving forward a bit
    	addParallel(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at speed while opening grabber
    	addSequential(new setIntakeContracted(false));
	}
}
