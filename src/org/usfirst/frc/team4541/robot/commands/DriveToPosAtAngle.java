package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

public class DriveToPosAtAngle extends Command {
	PIDController aController;
	PIDSource aSource;
	PIDOutput aOutput;
	PIDController yController;
	PIDSource ySource;
	PIDOutput yOutput;
	double rMovement;
	double fMovement;
	double angleObj;
	double distObj;
	public DriveToPosAtAngle(double aObj, double yObj) {
		this.requires(Robot.drivetrain);
		this.angleObj = aObj;
		this.distObj = yObj;
		aSource = new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
			
			@Override
			public double pidGet() {
				return Robot.gyro.getYaw();
			}
		};
		aOutput = new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				rMovement = output;
			}
		};
		aController = new PIDController(.2, .01, 1.6, 0, aSource, aOutput); //same p,i,d as turn to angle
		aController.setInputRange(-180, 180);
		aController.setOutputRange(-1, 1);
		aController.setContinuous(true);
		aController.setPercentTolerance(1);
		SmartDashboard.putData(aController);
		
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
				return Robot.trackball.getPos()[1];
			}
		};
		yOutput = new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				fMovement = output;
			}
		};
		yController = new PIDController(0, 0, 0, 0, ySource, yOutput); //TODO: tune these values; all 0 for now. 
		yController.setInputRange(-50, 50);
		yController.setOutputRange(-0.7, 0.7);
		yController.setPercentTolerance(1);
		SmartDashboard.putData(yController);
	}
    protected void execute() {
    	aController.setSetpoint(angleObj);
    	yController.setSetpoint(distObj);
    	Robot.drivetrain.drive(fMovement, rMovement);
    }
    
    int counter = 0;
    protected boolean isFinished() {
        if (yController.onTarget() && aController.onTarget()) {
        	counter++;
        } else {
        	counter = 0;
        }
        return counter > 100;
    }

    protected void end() {
    	Robot.drivetrain.drive(0, 0);
    	aController.disable();
    	yController.disable();
    }

    protected void interrupted() {
    	Robot.drivetrain.drive(0, 0);
    	aController.disable();
    	yController.disable();
    }

}
