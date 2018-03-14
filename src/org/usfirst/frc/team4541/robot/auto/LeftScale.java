package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.auto.ZeroYaw;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftScale extends CommandGroup {
	public LeftScale() {
		addParallel(new setIntakeContracted(true));
    	addParallel(new ElevatorToHeight(ElevatorConstants.twoInches)); // move up elevator
    	addParallel(new ZeroYaw());
    	
    	addSequential(new DriveToPosAtAngle(0, 26 - AutoConstants.halfRobotWidth));
    	addSequential(new TurnToAngle(-45));
    	addSequential(new ElevatorToHeight(ElevatorConstants.maxElevatorHeight));
    	addSequential(new DriveToPosAtAngle(-45, 1));
    	
    	addParallel(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at speed while opening grabber
    	addSequential(new setIntakeContracted(false));
	}
}