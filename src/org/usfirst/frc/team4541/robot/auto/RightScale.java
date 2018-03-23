package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.ShiftGear;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.auto.ZeroYaw;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorHome;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class RightScale extends CommandGroup {
	public RightScale() {
		addSequential(new ShiftGear(true));
		addParallel(new setIntakeContracted(true));
    	addParallel(new ElevatorToHeight(ElevatorConstants.threeInches)); // move up elevator
    	addParallel(new ZeroYaw());
    	addSequential(new TimedCommand(1.5));
    	
    	addSequential(new DriveToPosAtAngle(22, 0));
    	addSequential(new ElevatorToHeight(ElevatorConstants.maxElevatorHeight));
    	addSequential(new TurnToAngle(-45));
    	addSequential(new DriveToPosAtAngle(0.5, -45, true));
    	addParallel(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at speed while opening grabber
//    	addSequential(new setIntakeContracted(false));
    	addSequential(new TimedCommand(2));
    	
    	addSequential(new DriveToPosAtAngle(-2, -45, true)); //back away from the switch
    	addSequential(new setIntakeSpeed(0));
    	addSequential(new ElevatorToHeight(ElevatorConstants.twoInches));
	}
}