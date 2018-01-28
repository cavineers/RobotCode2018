package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.motionProfiling.MotionProfileHandler;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.robot.Robot;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;


public class DrivePath extends Command {
	MotionProfileHandler rightHandler;
	MotionProfileHandler leftHandler;
    public DrivePath(PATHS path) {
    	requires(Robot.drivetrain);
    	rightHandler = new MotionProfileHandler(Robot.drivetrain.getRightTalon(), PathHandler.getRightPointsForPath(path));
    	leftHandler = new MotionProfileHandler(Robot.drivetrain.getLeftTalon(), PathHandler.getLeftPointsForPath(path));
    	Robot.drivetrain.getRightTalon().selectProfileSlot(0, 0);
    	Robot.drivetrain.getLeftTalon().selectProfileSlot(0, 0);
    	rightHandler.reset();
    	leftHandler.reset();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftHandler.startMotionProfile();
    	rightHandler.startMotionProfile();
    	
    	Robot.drivetrain.getRightTalon().setInverted(true);
    	Robot.drivetrain.getRightSlaveTalon().setInverted(true);
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	rightHandler.control();
    	leftHandler.control();
    	
    	Robot.drivetrain.getRightTalon().set(ControlMode.MotionProfile, rightHandler.getSetValue().value);
    	Robot.drivetrain.getLeftTalon().set(ControlMode.MotionProfile, leftHandler.getSetValue().value);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	MotionProfileStatus rightStatus = new MotionProfileStatus();
    	Robot.drivetrain.getRightTalon().getMotionProfileStatus(rightStatus);
    	MotionProfileStatus leftStatus = new MotionProfileStatus();
    	Robot.drivetrain.getLeftTalon().getMotionProfileStatus(leftStatus);
        return rightStatus.isLast && leftStatus.isLast;
    }

    // Called once after isFinished returns true
    protected void end() {
    	rightHandler.reset();
    	leftHandler.reset();
    	Robot.drivetrain.getRightTalon().setInverted(false);
    	Robot.drivetrain.getRightSlaveTalon().setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	rightHandler.reset();
    	leftHandler.reset();
    	Robot.drivetrain.getRightTalon().setInverted(false);
    	Robot.drivetrain.getRightSlaveTalon().setInverted(false);
    }
}
