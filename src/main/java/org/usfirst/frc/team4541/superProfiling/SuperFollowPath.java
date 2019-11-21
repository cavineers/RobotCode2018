package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

//command responsible for running/updating super profiles

public class SuperFollowPath extends Command {
	CombinedSuperFollower csFollower;
	SuperProfile profile;
	SuperHeadingAdjuster headingAdjuster;
	double startTime; //start time in ms
	public SuperFollowPath(String pathName) {
		requires(Robot.drivetrain);
		csFollower = new CombinedSuperFollower();
		profile = new SuperProfile(pathName);
	}
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp() * 1000;
		Robot.drivetrain.getRightTalon().setSelectedSensorPosition(0, 0, 0);
    	Robot.drivetrain.getLeftTalon().setSelectedSensorPosition(0, 0, 0);
    	Robot.gyro.zeroYaw();
		Robot.drivetrain.leftMotor1.setInverted(true);
		Robot.drivetrain.leftMotor2.setInverted(true);
		Robot.drivetrain.rightMotor1.setInverted(false);
		Robot.drivetrain.rightMotor2.setInverted(false);
//		headingAdjuster = new SuperHeadingAdjuster();
	}
	@Override
	protected void execute() {
		long currentTime = Math.round((Timer.getFPGATimestamp() * 1000) - startTime);
		CombinedSetpoint idealSetpoint = profile.getCombinedSetpointForTime(currentTime);
		
		//account for gyro heading
		double angleError = SuperHeadingAdjuster.standardize(Math.toDegrees(idealSetpoint.heading)) - Robot.gyro.getYaw();
//		CombinedSetpoint realSetpoint = headingAdjuster.getAdjustedCombinedSetpointForHeading(idealSetpoint, angleError, currentTime);
		
		csFollower.update(idealSetpoint, Double.valueOf(currentTime));
	}
	public void update() {
		this.execute();
	}
	@Override
	protected void end() {
		Robot.drivetrain.leftMotor1.setInverted(true);
		Robot.drivetrain.leftMotor2.setInverted(true);
		Robot.drivetrain.rightMotor1.setInverted(true);
		Robot.drivetrain.rightMotor2.setInverted(true);
	}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
