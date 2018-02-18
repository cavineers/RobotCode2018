package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.robot.OI;
import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class ManualMoveElevator extends PIDCommand {
	boolean didSavePos;
    public ManualMoveElevator() {
//    	super(0.0001,0,0);
    	super(0.00007, 0.000001, 0.0001);
    	requires(Robot.elevator);
		this.getPIDController().setContinuous(false);
		this.getPIDController().setInputRange(0, Constants.maxElevatorHeight);
		this.getPIDController().setOutputRange(-1, 1);
		this.getPIDController().setAbsoluteTolerance(5);
		this.getPIDController().enable();
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

    protected void end() {
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
		Robot.elevator.setElevatorSpeed(output);
		
	}
}
