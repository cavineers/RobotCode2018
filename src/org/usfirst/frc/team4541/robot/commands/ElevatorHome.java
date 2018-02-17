package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorHome extends Command {
	
	double twoInches = 100; //(number of pulses for two inches)
    boolean isComplete = false;
    public ElevatorHome() {
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.getPIDVel().disable();
    	Robot.elevator.getPIDMotorOutput().disable();
    	Robot.elevator.getElevatorMotor().setSelectedSensorPosition(0,0,0); // zeroes encoder
    	Robot.elevator.getElevatorMotor().setNeutralMode(NeutralMode.Brake);
    	setTimeout(5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	while(Robot.elevator.getElevatorPos() < twoInches) {
    		Robot.elevator.getElevatorMotor().set(ControlMode.PercentOutput, .05);
    	}
    	Robot.elevator.getElevatorMotor().set(0);
    	
    	while(!Robot.elevator.getLimitSwitch().get()) {// until limit switch is pressed
    	Robot.elevator.getElevatorMotor().set(ControlMode.PercentOutput, -.05);
        }
       
        Robot.elevator.getElevatorMotor().setSelectedSensorPosition(0,0,0); // zeroes encoder
        Robot.elevator.getElevatorMotor().stopMotor();
        isComplete = true;
        
    }   

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isComplete || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
