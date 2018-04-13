package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

//command responsible for running/updating super profiles

public class SuperFollowPath extends Command {
	CombinedSuperFollower csFollower;
	SuperProfile profile;
	double startTime; //start time in ms
	public SuperFollowPath(String pathName) {
		requires(Robot.drivetrain);
		csFollower = new CombinedSuperFollower();
		profile = new SuperProfile(pathName);
	}
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp() * 1000;
	}
	@Override
	protected void execute() {
		if (!profile.hasValidPath()) {
			System.out.print("ERROR: UNABLE TO LOAD SUPERPATH; TRIED LOADING " + profile.getErrorPath());
			return;
		}
		long currentTime = Math.round((Timer.getFPGATimestamp() * 1000) - startTime);
		csFollower.update(profile.getCombinedSetpointForTime(currentTime), Double.valueOf(currentTime));
	}
	@Override
	protected boolean isFinished() {
		return false;
	}

}
