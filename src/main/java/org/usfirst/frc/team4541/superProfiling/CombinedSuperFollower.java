package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CombinedSuperFollower {
	SuperFollower rightFollower;
	SuperFollower leftFollower;
	
	public CombinedSuperFollower() {
		rightFollower = new SuperFollower(SuperConstants.kP, SuperConstants.kI, SuperConstants.kV, SuperConstants.mKffv, SuperConstants.mKffa);
		leftFollower = new SuperFollower(SuperConstants.kP, SuperConstants.kI, SuperConstants.kV, SuperConstants.mKffv, SuperConstants.mKffa);
	}
	public void update(CombinedSetpoint cSetpoint, Double startTime) {
		//get updated speeds from followers
		double rMotorOut = rightFollower.update(cSetpoint.getRightSetpoint(), Robot.getCurrentRobotState().getRightSide(), startTime);
		double lMotorOut = leftFollower.update(cSetpoint.getLeftSetpoint(), Robot.getCurrentRobotState().getLeftSide(), startTime);
		
		//set speeds to drivetrain
		Robot.drivetrain.profileDrive(lMotorOut, rMotorOut);
		
	}
	
}
