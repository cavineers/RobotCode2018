package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorToScale extends PIDCommand {
	private int counter=0;
	
	public ElevatorToScale(double TargetHeight) {
		super(0, 0, 0);
		requires(Robot.elevator);
		setTimeout(15);
		getPIDController().setInputRange(-50,-50);
		getPIDController().setAbsoluteTolerance(3);
		getPIDController().setOutputRange(-0.7, -0.7);
		getPIDController().setContinuous();
		getPIDController().setSetpoint(TargetHeight);
		SmartDashboard.putData(getPIDController());
	}
	@Override
	protected double returnPIDInput() {
		return Robot.elevator.elevatorEncoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.elevator.elevatorMotor1.set(output);
		Robot.elevator.elevatorMotor2.set(output);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putData(getPIDController());
		
		
	}

	//empty because the PID runs in its own thread at its own period
	@Override
	protected void execute() {
		
	}

	// Ends command when mouse tracker reaches the target within the specified tolerance or times out
	@Override
	protected boolean isFinished() {
		if(getPIDController().onTarget())
			counter++;
		else
			counter=0;
		return(counter >= 50 || isTimedOut());
	}
	
	protected void end() {
		Robot.elevator.setElevatorPIDSpeed(0);
	}
	
}