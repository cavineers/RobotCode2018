package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.motionProfiling.MP_Handler_3;
import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.Robot;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class DrivePath_2 extends Command{
	MP_Handler_3 rightHandler;
	MP_Handler_3 leftHandler;
	MotionProfileStatus rightStatus;
	MotionProfileStatus leftStatus;
	
	public DrivePath_2(PATHS path, int timeOut) {
		requires(Robot.drivetrain);
		this.setTimeout(timeOut);
		rightHandler = new MP_Handler_3(Robot.drivetrain.getRightTalon(), PathHandler.getRightPointsForPath(path));
		leftHandler = new MP_Handler_3(Robot.drivetrain.getLeftTalon(), PathHandler.getLeftPointsForPath(path));
        Robot.drivetrain.getRightTalon().selectProfileSlot(0, 0);
        Robot.drivetrain.getLeftTalon().selectProfileSlot(0, 0);
	}
	
	protected void initialize() {
		Robot.drivetrain.getRightTalon().setInverted(false);
		Robot.drivetrain.getRightSlaveTalon().setInverted(false);
		rightHandler.reset();
		leftHandler.reset();
		Robot.drivetrain.getRightTalon().set(ControlMode.MotionProfile, 0);
		Robot.drivetrain.getLeftTalon().set(ControlMode.MotionProfile, 0);
	}
	
	protected void execute() {
		if (rightHandler.isReady() && leftHandler.isReady()) {
			rightHandler.start();
			leftHandler.start();
		}
		rightStatus = rightHandler.control();
		leftStatus = leftHandler.control();
	}
	
	protected boolean isFinished() {
		return rightStatus.isLast || leftStatus.isLast || this.isTimedOut();
	}
	
	protected void isInterrupted() {
		end();
	}
	
	protected void end() {
		rightHandler.reset();
		leftHandler.reset();
		Robot.drivetrain.getRightTalon().setInverted(true);
		Robot.drivetrain.getRightSlaveTalon().setInverted(true);
		
		Robot.drivetrain.getRightTalon().set(ControlMode.PercentOutput, 0);
		
		Robot.drivetrain.getLeftTalon().set(ControlMode.PercentOutput, 0);
	}
	
}
