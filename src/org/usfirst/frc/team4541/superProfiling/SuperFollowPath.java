package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

//command responsible for running/updating super profiles

public class SuperFollowPath extends Command {
	CombinedSuperFollower csFollower;
	double startTime; //start time in ms
	public SuperFollowPath() {
		csFollower = new CombinedSuperFollower();
	}
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp() * 1000;
	}
	@Override
	protected void execute() {
		csFollower.update(); //TODO: figure out SuperPath handling
	}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
