package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.commands.ShiftGear;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;
import org.usfirst.frc.team4541.robot.commands.setIntakeSpeed;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.auto.ZeroYaw;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorToHeight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class RightOppScalePointTurn extends CommandGroup{
	public  RightOppScalePointTurn() {
		addSequential(new ShiftGear(true));
		addSequential(new setIntakeContracted(true));
		addSequential(new ElevatorToHeight(ElevatorConstants.threeInches)); // move up elevator
		addSequential(new ZeroYaw());
		addSequential(new TimedCommand(2));
		
		addSequential(new DriveToPosAtAngle(18, 0)); //drive past the switch
    	addSequential(new TurnToAngle(-90));
    	addSequential(new DriveToPosAtAngle(16, -90));	//drive over to the scale
    	addSequential(new TurnToAngle(0));		//turn to the scale
    	
    	addSequential(new ElevatorToHeight(ElevatorConstants.maxElevatorHeight));
    	addSequential(new DriveToPosAtAngle(3, 0, true));
    	
    	addSequential(new setIntakeSpeed(AutoConstants.ejectVelocity)); //spin wheels at speed while opening grabber
//    	addSequential(new setIntakeContracted(false));
    	
    	addSequential(new DriveToPosAtAngle(-2, 0, true)); //back away from the switch
    	addSequential(new setIntakeSpeed(0));
    	addSequential(new ElevatorToHeight(ElevatorConstants.twoInches));
    	
    	
    	
	}
}
