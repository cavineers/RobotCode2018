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
import org.usfirst.frc.team4541.robot.commands.ShiftGear;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class LeftSwitchSide extends CommandGroup {

    public LeftSwitchSide() {
    	addSequential(new ShiftGear(true));
    	addSequential(new ZeroYaw());
    	addSequential(new setIntakeContracted(true));
    	addSequential(new ElevatorToHeight(ElevatorConstants.twoInches));
    	addSequential(new TimedCommand(1));
    	addParallel(new ElevatorToHeight(ElevatorConstants.switchHeight)); // move up elevator
    	
    	addSequential(new DriveToPosAtAngle(13, 0)); //drive to side of left switch
    	addSequential(new TurnToAngle(90));
    	addSequential(new DriveToPosAtAngle(1.5, 90));
    	
    	addSequential(new DriveForward(AutoConstants.driveForwardVel, AutoConstants.driveForwardTime)); //make sure that we're touching the wall by driving at half speed for half a second
    	addSequential(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at half speed while opening grabber
    	addSequential(new setIntakeContracted(false));
    	
    	addSequential(new TimedCommand(2));
    	addSequential(new DriveToPosAtAngle(-2.5, 90, true)); //back away from the switch
    	addSequential(new setIntakeSpeed(0));
    	addSequential(new ElevatorToHeight(ElevatorConstants.twoInches));
    }
}
