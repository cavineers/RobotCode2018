package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.robot.ElevatorConstants;
import org.usfirst.frc.team4541.robot.OI;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.ManualMoveElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
	public WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);
	private boolean maintainingPos = false; // whether the elevator is using input from controller or PID
	
	private PIDController pidVel;
	private PIDController pidMotorOutput;
	

	
	// move to constant class
	private double P_Out = 0;
	private double I_Out = 0;
	private double D_Out = 0;
	
	private double P_Vel = 0;
	private double I_Vel = 0;
	private double D_Vel = 0;
	private double F_Vel = 0;
	
	DigitalInput limitSwitch;
	
	
	
	public Elevator() {

		
	    limitSwitch = new DigitalInput(0); // change input later
	    
		
		
		elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		elevatorMotor.setSensorPhase(true); /* keep sensor and motor in phase */
		
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
				Robot.elevator.getPIDMotorOutput().setSetpoint(d);
			}
		});
	
		pidVel.setInputRange(ElevatorConstants.minElevatorHeight, ElevatorConstants.maxElevatorHeight);
		pidVel.setSetpoint(0);
		pidVel.setOutputRange(-100, 100);
		pidVel.setContinuous(false);
		pidVel.setPercentTolerance(1);
		pidVel.disable();
		//output of pidVel is setpoint of pidMotorOutput
		//SmartDashboard.putData(pidVel.getSmartDashboardType(), pidVel);
		
		pidMotorOutput = new PIDController(P_Out, I_Out, D_Out, new PIDSource() {
			PIDSourceType out_sourceType = PIDSourceType.kDisplacement;
			
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
		});
		
		pidMotorOutput.setInputRange(-100, 100);
		pidMotorOutput.setOutputRange(-1, 1);
		pidMotorOutput.setContinuous(false);
		pidMotorOutput.setPercentTolerance(1);
		pidMotorOutput.setSetpoint(0);
		pidMotorOutput.disable();
		
	}
	
	
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualMoveElevator());
    }
    public WPI_TalonSRX getElevatorMotor() {
    	return elevatorMotor;
    }
  /*  public void setElevatorSpeed(double speed) {
    	elevatorMotor.set(ControlMode.PercentOutput, speed);
    }*/
    
    public double getElevatorPos() {
    	return Robot.elevator.getElevatorMotor().getSelectedSensorPosition(0);
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
   
	
}

