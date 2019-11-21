package frc.robot.subsystems;

import frc.robot.ElevatorConstants;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.ManualMoveElevator;
import frc.robot.commands.elevator.NewManual;
import frc.robot.commands.elevator.TestMotorOutput;

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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
	public WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);
	private PIDController pidVel;
	private PIDController pidMotorOutput;
	private double manualVelocity = 9999;
	private double prevOutput = 0;
	private int loop = 0;

	
	//same values- based on 800 maxA with old grabber
	/*private double P_Out_Up = 0.000005;
	private double D_Out_Up = 0.00005;
	private double F_Out_Up = .65/3000.0;

	private double P_Vel_Up = 1.0;
	private double D_Vel_Up = 4.0;

	private double P_Out_Down = 0.000005;
	private double D_Out_Down = 0.00005;
	private double F_Out_Down = .65/3000.0;

	private double P_Vel_Down = 1.0;
	private double D_Vel_Down = 4.0; */
	
	private double P_Out_Up = 0.000005;
	private double D_Out_Up = 0.00005;
	private double F_Out_Up = .65/3000.0;

	private double P_Vel_Up = 1.0;
	private double D_Vel_Up = 4.0;

	


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
				if(Math.abs(d-prevOutput) > ElevatorConstants.maxA/4) {
					if(d>prevOutput) {
						d=prevOutput + ElevatorConstants.maxA/4;
					}
					else {
						d=prevOutput - ElevatorConstants.maxA/4;
					}
				}
				
				prevOutput = d;
     
				if (manualVelocity == 9999)
					getPIDMotorOutput().setSetpoint(d);

				else if (manualVelocity > 0) {
					getPIDMotorOutput().setSetpoint(Math.min(d, manualVelocity));
					loop = 0;
				}
				else if (manualVelocity < 0) {
					getPIDMotorOutput().setSetpoint(Math.max(d, manualVelocity));
					loop = 0;
				}
				else if (manualVelocity == 0) {
					getPIDMotorOutput().setSetpoint(0);
					loop++;
				}
				if(loop == 10) {
					manualVelocity = 9999;
					loop = 0;
				}

			}
		}, period);

		pidVel.setInputRange(ElevatorConstants.minElevatorHeight, ElevatorConstants.maxElevatorHeight);
		pidVel.setOutputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
		pidVel.setContinuous(false);
		pidVel.setPercentTolerance(5);
		// output of pidVel is setpoint of pidMotorOutput

		pidMotorOutput.setInputRange(-ElevatorConstants.maxSpeed, ElevatorConstants.maxSpeed);
		pidMotorOutput.setOutputRange(-1, 1);
		pidMotorOutput.setContinuous(false);
		pidMotorOutput.setPercentTolerance(1);
		pidMotorOutput.setSetpoint(0);

		elevatorMotor.setSelectedSensorPosition(-500, 0, 0);
		elevatorMotor.setNeutralMode(NeutralMode.Brake);
		
//		SmartDashboard.putData(pidVel);
//		SmartDashboard.putData(pidMotorOutput);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ManualMoveElevator());
		// setDefaultCommand(new TestMotorOutput());
	}

	/*
	 * called in execute() for all commands using elevator; changes PID vals based
	 * on direction
	 */
	

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

	public void setManualVelocity(double trigger) {
		manualVelocity = trigger;
	}
	
	public double getElevatorVel() {
		return elevatorMotor.getSelectedSensorVelocity(0);
	}

}
