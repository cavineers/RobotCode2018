package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.motionProfiling.Constants;
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
	LinearDigitalFilter filter;
	public DriveToPosAtAngle(double yObj, double aObj) {
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
		aController = new PIDController(0.04, 0, 0, 0, aSource, aOutput); //same p,i,d as turn to angle
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
				return pulsesToFt(Robot.drivetrain.getDistanceMoved());
			}
		};
		yOutput = new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				fMovement = output;
			}
		};
		yController = new PIDController(0.8, 0.0, 1.3, 0, ySource, yOutput);
		yController.setInputRange(0, yObj);
		yController.setOutputRange(-0.7, 0.7);
		yController.setPercentTolerance(5);
		SmartDashboard.putData(yController);
		filter = LinearDigitalFilter.movingAverage(new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return yController.getError();
			}
			
		}, 10);
	}
	protected void initialize() {
    	aController.enable();
    	yController.enable();
    	filter.reset();
    	Robot.drivetrain.getRightTalon().setSelectedSensorPosition(0, 0, 0);
    	Robot.drivetrain.getLeftTalon().setSelectedSensorPosition(0, 0, 0);
	}
    protected void execute() {
    	aController.setSetpoint(angleObj);
    	yController.setSetpoint(distObj);
    	SmartDashboard.putNumber("YController Setpoint", distObj);
    	SmartDashboard.putNumber("avg error", filter.get());
    	Robot.drivetrain.drive(fMovement, rMovement);
    	filter.pidGet();
    }
    
    int counter = 0;
    protected boolean isFinished() {
//        if (yController.onTarget() && aController.onTarget() ) {
//        	counter++;
//        } else {
//        	counter = 0;
//        }
//        return counter > 30;
        if (yController.onTarget() && aController.onTarget() && filter.get() < 0.2) {
        	return true;
        }
        return false;
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
    private double pulsesToFt(double pulses) {
    	return pulses / Constants.kSensorUnitsPerRotation;
    }

}
