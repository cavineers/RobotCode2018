package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.elevator.NewManual;
import org.usfirst.frc.team4541.robot.commands.elevator.TestMotorOutput;

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
	public WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);
	private PIDController pidVel;
	private PIDController pidMotorOutput;
	private double manualVelocity = 9999;

	private double P_Out_Up = 0.00001;
	private double D_Out_Up = 0.0001;
	private double F_Out_Up = 1.0 / 3000.0;

	private double P_Vel_Up = 0.95;
	private double D_Vel_Up = 1.0;

	private double P_Out_Down = 0.00001;
	private double D_Out_Down = 0.0005;
	private double F_Out_Down = 0.0001;

	private double P_Vel_Down = 0.95;
	private double D_Vel_Down = 1.0;

	private double period = .025;

	public DigitalInput limitSwitch;

	public Elevator() {

		limitSwitch = new DigitalInput(0); // change input later

		elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		elevatorMotor.setSensorPhase(true); /* keep sensor and motor in phase */

		pidMotorOutput = new PIDController(P_Out_Up, 0, D_Out_Up, F_Out_Up, new PIDSource() {
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

		pidVel = new PIDController(P_Vel_Up, 0, D_Vel_Up, new PIDSource() {
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

		}, new PIDOutput() {
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
		}, period);

		pidVel.setInputRange(ElevatorConstants.minElevatorHeight, ElevatorConstants.maxElevatorHeight);
		pidVel.setSetpoint(0);
		pidVel.setOutputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
		pidVel.setContinuous(false);
		pidVel.setPercentTolerance(.5);
		pidVel.enable();
		// output of pidVel is setpoint of pidMotorOutput

		pidMotorOutput.setInputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
		pidMotorOutput.setOutputRange(-1, 1);
		pidMotorOutput.setContinuous(false);
		pidMotorOutput.setPercentTolerance(1);
		pidMotorOutput.setSetpoint(0);
		pidMotorOutput.enable();

		elevatorMotor.setSelectedSensorPosition(-500, 0, 0);
		elevatorMotor.setNeutralMode(NeutralMode.Brake);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new NewManual());
		// setDefaultCommand(new TestMotorOutput());
	}

	/*
	 * called in execute() for all commands using elevator; changes PID vals
	 * based on direction
	 */
	public void updatePIDVals() {
		if (pidVel.get() > 0) { // moving up
			this.getPIDVel().setP(P_Vel_Up);
			this.getPIDVel().setI(0);
			this.getPIDVel().setD(D_Vel_Up);

			this.getPIDMotorOutput().setF(F_Out_Up);
			this.getPIDMotorOutput().setP(P_Out_Up);
			this.getPIDMotorOutput().setI(0);
			this.getPIDMotorOutput().setD(D_Out_Up);
		} else {
			this.getPIDVel().setP(P_Vel_Down);
			this.getPIDVel().setI(0);
			this.getPIDVel().setD(D_Vel_Down);
			
			this.getPIDMotorOutput().setF(F_Out_Down);
			this.getPIDMotorOutput().setP(P_Out_Down);
			this.getPIDMotorOutput().setI(0);
			this.getPIDMotorOutput().setD(D_Out_Down);
		}
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
