package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends PIDCommand {
	private int counter=0;
	
	public TurnToAngle(double TargetAngle) {
		super(.2, .01, 1.6);
		requires(Robot.drivetrain);
		setTimeout(30);
		getPIDController().setInputRange(-180,180);
		getPIDController().setAbsoluteTolerance(.5);
		getPIDController().setOutputRange(-.7, .7);
		getPIDController().setContinuous();
		getPIDController().setSetpoint(TargetAngle);
		
	}
	@Override
	protected double returnPIDInput() {
		return Robot.gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		//SmartDashboard.putNumber("Speed: ", output);
		Robot.drivetrain.drive(0, output);
	}
	
	@Override
	protected void initialize() {
		SmartDashboard.putData(getPIDController());
		
		//getPIDController().getSmartDashboardType(),
		
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
		Robot.drivetrain.drive(0,0);
	}
	
}