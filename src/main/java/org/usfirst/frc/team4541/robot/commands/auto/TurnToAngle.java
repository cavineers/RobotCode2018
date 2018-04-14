package org.usfirst.frc.team4541.robot.commands.auto;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends PIDCommand {
	LinearDigitalFilter filter;
	double lastAngle;
	double targetAngle;
	double startTime;
	public TurnToAngle(double TargetAngle) {
//		super(0.020, 0.00023, 0.020);// 0.023 for p
		super(0.035, 0, 0.07);
		this.targetAngle = TargetAngle;
		requires(Robot.drivetrain);
		setTimeout(15);
		getPIDController().setInputRange(-180,180);
		getPIDController().setAbsoluteTolerance(5);
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setContinuous();
//		SmartDashboard.putData(getPIDController());
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
				return Robot.gyro.getYaw() - lastAngle;
			}
			
		}, 20);
	}
	@Override
	protected double returnPIDInput() {
		return Robot.gyro.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive(0, output);
	}
	
	@Override
	protected void initialize() {
		getPIDController().setSetpoint(targetAngle);
		startTime = Timer.getFPGATimestamp();
	}

	//empty because the PID runs in its own thread at its own period
	@Override
	protected void execute() {
		lastAngle = Robot.gyro.getYaw();
	}

	// Ends command when mouse tracker reaches the target within the specified tolerance or times out
	@Override
	protected boolean isFinished() {
		if (getPIDController().onTarget() && filter.pidGet() == 0 && (Timer.getFPGATimestamp() - this.startTime) > 1) {
        	return true;
        }
		return false;
	}
	
	protected void end() {
		Robot.drivetrain.drive(0,0);
	}
	
}