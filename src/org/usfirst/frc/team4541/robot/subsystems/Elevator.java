package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.ElevatorConstants;

import org.usfirst.frc.team4541.robot.commands.NewManual;
import org.usfirst.frc.team4541.robot.commands.TestMotorOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	public WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(0);
	private PIDController pidVel;
	private PIDController pidMotorOutput;
	private double manualVelocity = 9999;

	private double P_Out = 0.00001;
	private double I_Out = 0;
	private double D_Out = 0.0001;
	private double F_Out = .0005;

	private double P_Vel = 0.1;
	private double I_Vel = 0;
	private double D_Vel = 0;

	private double period = .025; 
	
	public DigitalInput limitSwitch;

	public boolean limitSwitch2;

	public Elevator() {

		limitSwitch = new DigitalInput(0); // change input later

		elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		elevatorMotor.setSensorPhase(true); /* keep sensor and motor in phase */

		pidMotorOutput = new PIDController(P_Out, I_Out, D_Out, F_Out, new PIDSource() {
			PIDSourceType out_sourceType = PIDSourceType.kRate;

			@Override
			public double pidGet() {
				return elevatorMotor.getSelectedSensorVelocity(0);
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				out_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return out_sourceType;
			}
		},

				new PIDOutput() {
					@Override
					public void pidWrite(double d) {
						elevatorMotor.set(ControlMode.PercentOutput, d);
					}
				}, period);

		pidVel = new PIDController(P_Vel, I_Vel, D_Vel, new PIDSource() {
			PIDSourceType vel_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return elevatorMotor.getSelectedSensorPosition(0);
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				vel_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return vel_sourceType;
			}

		},

				new PIDOutput() {
					@Override
					public void pidWrite(double d) {
						// compare what PID says to trigger

						if (manualVelocity == 9999)
							getPIDMotorOutput().setSetpoint(d);

						else if (manualVelocity > 0)
							getPIDMotorOutput().setSetpoint(Math.min(d, manualVelocity));
						else
							getPIDMotorOutput().setSetpoint(Math.max(d, manualVelocity));

					}
				}, period );

		pidVel.setInputRange(ElevatorConstants.minElevatorHeight, ElevatorConstants.maxElevatorHeight);
		pidVel.setSetpoint(0);
		pidVel.setOutputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
		pidVel.setContinuous(false);
		pidVel.setPercentTolerance(.05);
		pidVel.disable();
		// output of pidVel is setpoint of pidMotorOutput

		pidMotorOutput.setInputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
		pidMotorOutput.setOutputRange(-1, 1);
		pidMotorOutput.setContinuous(false);
		pidMotorOutput.setPercentTolerance(1);
		pidMotorOutput.setSetpoint(0);
		pidMotorOutput.disable();

		elevatorMotor.setSelectedSensorPosition(0, 0, 0); // TODO: zeroes encoder
		elevatorMotor.setNeutralMode(NeutralMode.Brake);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new NewManual());
		//setDefaultCommand(new TestMotorOutput());
	}

	public WPI_TalonSRX getElevatorMotor() {
		return elevatorMotor;
	}

	public double getElevatorPos() {
		return getElevatorMotor().getSelectedSensorPosition(0);
	}

	public PIDController getPIDVel() {
		return pidVel;
	}

	public PIDController getPIDMotorOutput() {
		return pidMotorOutput;
	}

	public DigitalInput getLimitSwitch() {
		return limitSwitch;
	}

	public void setTriggerValue(double trigger) {
		manualVelocity = trigger;
	}

}
