package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.ZeroYaw;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToAutoLine extends CommandGroup {
	public DriveToAutoLine() {
		addParallel(new ZeroYaw());
		addParallel(new setIntakeContracted(true));
		addParallel(new ElevatorToHeight(ElevatorConstants.twoInches)); // move up elevator
		
		addSequential(new DriveToPosAtAngle(10, 0));
		
	}
	
}
