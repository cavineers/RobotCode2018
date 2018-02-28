package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team4541.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

public class DriveToPos extends Command {
	PIDController yController;
	PIDSource ySource;
	PIDOutput yOutput;
	double fMovement;
	double distObj;
	public DriveToPos(double aObj, double yObj) {
		this.requires(Robot.drivetrain);
		this.distObj = yObj;
		
		yController = new PIDController(0, 0, 0, 0, ySource, yOutput); //TODO: tune these values; all 0 for now. 
		yController.setInputRange(-50, 50);
		yController.setOutputRange(-0.7, 0.7);
		yController.setPercentTolerance(1);
		ySource = new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
			
			@Override
			public double pidGet() {
				return Robot.drivetrain.getDistanceMoved();
			}
		};
		yOutput = new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				fMovement = output;
			}
		};
		yController.enable();
	}
    protected void execute() {
    	yController.setSetpoint(distObj);
    	Robot.drivetrain.drive(fMovement, 0);
    }
    
    int counter = 0;
    protected boolean isFinished() {
        if (yController.onTarget()) {
        	counter++;
        } else {
        	counter = 0;
        }
        return counter > 100;
    }

    protected void end() {
    	Robot.drivetrain.drive(0, 0);
    	yController.disable();
    }

    protected void interrupted() {
    	Robot.drivetrain.drive(0, 0);
    	yController.disable();
    }

}
