package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevator extends Command {

    public MoveElevator() {
    	requires(Robot.elevator);
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.setElevatorSpeed(Robot.oi.getJoystick().getRawAxis(2), Robot.oi.getJoystick().getRawAxis(3)); //TODO: set real axis for elevator
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //Runs until interrupted
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setElevatorPIDSpeed(0);
    }
}
