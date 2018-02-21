package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.robot.OI;
import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ManualMoveElevator extends PIDCommand {
	boolean didSavePos;
    public ManualMoveElevator() {
//    	super(0.0001,0,0);
    	super(0.000075, 0, 0.0001); //0.000001 for i
    	requires(Robot.elevator);
		this.getPIDController().setContinuous(false);
		this.getPIDController().setInputRange(0, Constants.maxElevatorHeight);
		this.getPIDController().setOutputRange(-1, 1);
		this.getPIDController().setAbsoluteTolerance(10);
		this.getPIDController().enable();
		Robot.elevator.elevatorMotor.setSelectedSensorPosition(0,0,0);
		this.setSetpoint(Robot.elevator.getElevatorPos());
		didSavePos = true;
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.currentTriggerSetting != OI.TRIG_MODE.ELEVATOR) {
    		return;
    	}
    	double elevPos  = Robot.elevator.getElevatorPos();
    	double upTrig   = Robot.oi.getJoystick().getRawAxis(3);
    	double downTrig = Robot.oi.getJoystick().getRawAxis(2);
    	if (upTrig <= 0.05 && downTrig <= 0.05 || upTrig  > 0.05 && downTrig > 0.05) { //if neither are pressed, or if both are pressed, do nothing
    		//both or neither triggers pressed, maintain current pos
    		if (!didSavePos) {
    			this.setSetpoint(elevPos);
    			didSavePos = true;
    		}
    	} else if (upTrig  > 0.05) {
    		didSavePos = false;
    		this.setSetpoint(elevPos + (upTrig * Constants.triggerCoefficient));
    		
    	} else if (downTrig > 0.05) {
    		didSavePos = false;
    		this.setSetpoint(elevPos - (downTrig * Constants.triggerCoefficient));
    	}
    	SmartDashboard.putNumber("elevator Setpoint: ", this.getSetpoint());
    	SmartDashboard.putData(this.getPIDController());
    	System.out.print(Timer.getFPGATimestamp() + ",");
    	System.out.print(Robot.elevator.elevatorMotor.getSelectedSensorVelocity(0) + ",");
    	System.out.print(this.getPIDController().get());
    	System.out.println();
    }
    
    protected void interrupted() {
    	didSavePos = false;
    }
    protected boolean isFinished() {
        return false;
    }
    public void setSetpoint(double setPoint) {
    	if (setPoint < 0) {
    		this.getPIDController().setSetpoint(0);
    	} else if (setPoint > Constants.maxElevatorHeight) {
    		this.getPIDController().setSetpoint(Constants.maxElevatorHeight);
    	} else {
    		this.getPIDController().setSetpoint(setPoint);
    	}
    }

    @Override
    protected void initialize() {
    	didSavePos = false;
    }
	@Override
	protected double returnPIDInput() {
		return Robot.elevator.getElevatorPos();
	}
	@Override
	protected void usePIDOutput(double output) {
//		double elevPos  = Robot.elevator.getElevatorPos();
//		if (elevPos > 20 && output < 0.05) {
//			Robot.elevator.setElevatorSpeed(0);
//		} else {
			Robot.elevator.setElevatorSpeed(output);
			SmartDashboard.putNumber("elevator output: ", output);
//		}
	}
}
