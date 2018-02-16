package org.usfirst.frc.team4541.robot.subsystems;

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
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	public WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);
	private boolean maintainingPos = false; // whether the elevator is using input from controller or PID
	
	public Elevator() {
		
		elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		elevatorMotor.setSensorPhase(true); /* keep sensor and motor in phase */
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualMoveElevator());
    }
    public WPI_TalonSRX getElevatorMotor() {
    	return elevatorMotor;
    }
    public void setElevatorSpeed(double speed) {
    	elevatorMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public double getElevatorPos() {
    	return Robot.elevator.getElevatorMotor().getSelectedSensorPosition(0);
    }
}

