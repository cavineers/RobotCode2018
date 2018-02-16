package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.robot.Robot;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class PIDMoveElevator extends PIDCommand {
	int counter = 0;
	double setPoint = 0;
    public PIDMoveElevator(double setPoint) {
    	super(0.00005,0,0);
        requires(Robot.elevator);
    	this.getPIDController().setContinuous(false);
		this.getPIDController().setInputRange(0, Constants.maxElevatorHeight);
		this.getPIDController().setOutputRange(-1, 1);
		this.getPIDController().setAbsoluteTolerance(300);
		this.getPIDController().enable();
		this.setPoint = setPoint;
    }

    protected void initialize() {
    	this.getPIDController().setSetpoint(setPoint);
    }

    protected void execute() {
    	
    }

    protected boolean isFinished() {
    	if(getPIDController().onTarget())
			counter++;
		else
			counter=0;
    	
		return(counter >= 50 || isTimedOut());    
	}

	@Override
	protected double returnPIDInput() {
		return Robot.elevator.getElevatorPos();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.elevator.setElevatorSpeed(output);
	}
	public void freePID() {
		this.getPIDController().free();
	}
}
