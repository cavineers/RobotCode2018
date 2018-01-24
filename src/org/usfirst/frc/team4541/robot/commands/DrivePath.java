package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.motionProfiling.MotionProfileHandler;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;


public class DrivePath extends Command {
	MotionProfileHandler rightHandler;
	MotionProfileHandler leftHandler;
    public DrivePath(PATHS path) {
    	requires(Robot.drivetrain);
    	rightHandler = new MotionProfileHandler(Robot.drivetrain.getRightTalon(), PathHandler.getRightPointsForPath(path));
    	leftHandler = new MotionProfileHandler(Robot.drivetrain.getLeftTalon(), PathHandler.getLeftPointsForPath(path));

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rightHandler.startMotionProfile();
    	leftHandler.startMotionProfile();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.getRightTalon().set(ControlMode.MotionProfile, rightHandler.getSetValue().value);
    	Robot.drivetrain.getLeftTalon().set(ControlMode.MotionProfile, leftHandler.getSetValue().value);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
