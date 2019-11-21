package frc.robot.commands.control;

import frc.robot.OI.TRIG_MODE;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeTriggerMode extends Command {
	private TRIG_MODE newMode;
	public ChangeTriggerMode(TRIG_MODE newMode) {
		this.newMode = newMode;
	}
	protected void initialize() {
		Robot.oi.currentTriggerSetting = newMode;
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
