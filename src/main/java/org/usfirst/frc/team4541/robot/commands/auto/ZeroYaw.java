package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import edu.wpi.first.wpilibj.command.Command;

public class ZeroYaw extends Command {

	public ZeroYaw() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gyro.zeroYaw();
    }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}
}