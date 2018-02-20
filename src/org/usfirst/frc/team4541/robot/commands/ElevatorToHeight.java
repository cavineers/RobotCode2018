package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorToHeight extends Command {
	double targetHeight;
	
	public ElevatorToHeight(double tHeight) {
	requires(Robot.elevator);
	targetHeight = tHeight;
	}
	
    
	protected void initiatlize() {
//		Robot.elevator.getPIDVel().enable();
//		Robot.elevator.getPIDMotorOutput().enable();
		this.setSetpoint(targetHeight);
		setTimeout(((ElevatorConstants.maxElevatorHeight/ElevatorConstants.maxSpeed)/10)*2);
		
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double elevPos  = Robot.elevator.getElevatorPos();
    	
    
    	
//    	if(elevPos > ElevatorConstants.upperDangerZone) {
//    		Robot.elevator.getPIDVel().setOutputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed/2);
//    	}
//    	else if(elevPos< ElevatorConstants.lowerDangerZone) {
//    		Robot.elevator.getPIDVel().setOutputRange(-ElevatorConstants.maxSpeed/2, ElevatorConstants.maxSpeed);
//    	}
//    	else {
//    		Robot.elevator.getPIDVel().setOutputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
//    	}
    }
    
    protected void interrupted() {
    	end();
    }
    protected boolean isFinished() {
//      return Robot.elevator.getPIDVel().onTarget() || isTimedOut();
      return false;
    }
    public void setSetpoint(double setPoint) {
//    	if (setPoint < 0) {
//    		Robot.elevator.getPIDVel().setSetpoint(0);
//    	} else if (setPoint > ElevatorConstants.maxElevatorHeight) {
//    		Robot.elevator.getPIDVel().setSetpoint(ElevatorConstants.maxElevatorHeight);
//    	} else {
//    		Robot.elevator.getPIDVel().setSetpoint(setPoint);
//    	}
    }

    protected void end() {
    	
    }
    
    
	
}
