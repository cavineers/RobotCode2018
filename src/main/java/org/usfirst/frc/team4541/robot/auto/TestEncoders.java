package org.usfirst.frc.team4541.robot.auto;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.commands.auto.DriveForward;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestEncoders extends CommandGroup {
	public TestEncoders() {
		addSequential(new Command() {
			@Override
    		protected void initialize() {
				Robot.drivetrain.getRightTalon().setSelectedSensorPosition(0, 0, 0);
		    	Robot.drivetrain.getLeftTalon().setSelectedSensorPosition(0, 0, 0);
			}
			@Override
			protected boolean isFinished() {
				return true;
			}
		});
		
    	addSequential(new DriveForward(0.15, 0.3));
    	addSequential(new Command() {
    		@Override
    		protected void initialize() {
    			int right = Robot.drivetrain.getRightTalon().getSelectedSensorPosition(0);
    			int left = Robot.drivetrain.getLeftTalon().getSelectedSensorPosition(0);
    			if (right == 0 && left == 0) {
    				SmartDashboard.putString("ENCODER STATUS", "BOTH ERROR");
    			} else if (right == 0) {
    				SmartDashboard.putString("ENCODER STATUS", "RIGHT ERROR");
    			} else if (left == 0) {
    				SmartDashboard.putString("ENCODER STATUS", "LEFT ERROR");
    			} else {
    				SmartDashboard.putString("ENCODER STATUS", "NOMINAL");
    			}
    		}
			@Override
			protected boolean isFinished() {
				return true;
			}
    		
    	});
	}

}
