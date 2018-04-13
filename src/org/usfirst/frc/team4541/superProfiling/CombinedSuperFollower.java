package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.robot.Robot;

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
		
		//account for gyro heading
		double angle_difference = cSetpoint.heading - Robot.gyro.getYaw();
		double turn = SuperConstants.kRotationP * (angle_difference / 180.0);
		
		//set speeds to drivetrain
		Robot.drivetrain.profileDrive(lMotorOut + turn, rMotorOut - turn);
	}
	
}
