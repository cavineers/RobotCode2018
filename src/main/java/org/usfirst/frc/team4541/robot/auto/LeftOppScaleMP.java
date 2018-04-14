package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.ShiftGear;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;
import org.usfirst.frc.team4541.robot.commands.auto.DrivePath;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.auto.ZeroYaw;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class LeftOppScaleMP extends CommandGroup{
	public LeftOppScaleMP() {
		addSequential(new ShiftGear(true));
		addParallel(new setIntakeContracted(true));
    	addParallel(new ElevatorToHeight(ElevatorConstants.threeInches)); // move up elevator
    	addParallel(new ZeroYaw());
    	addSequential(new TimedCommand(1));
    	
    	addSequential(new DrivePath(PATHS.LEFT_OPP_SCALE));
    	addSequential(new ZeroYaw());
    	
    	addSequential(new ElevatorToHeight(ElevatorConstants.maxElevatorHeight));
    	addSequential(new TurnToAngle(-90));
    	
    	addSequential(new setIntakeSpeed(AutoConstants.ejectVelocity));
    	
	}
}
