package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.OI;
import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.MoveElevator;

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
	private WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.elevatorMotor1);
	private WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.elevatorMotor2);
	public Encoder elevatorEncoder = new Encoder(4, 5, false, EncodingType.k4X);
	private boolean maintainingPos = false; // whether the elevator is using input from controller or PID
	
	public PIDController elevatorController = new PIDController(0,0,0,0, Robot.oi.getPIDSource(OI.SENSOR.ENCODER_ELEVATOR), new PIDOutput() {
		@Override
		public void pidWrite(double output) {
			setElevatorSpeed(output);
		}
	});
	public Elevator() {
		elevatorController.setContinuous(false);
		elevatorController.setInputRange(0, 60);
		elevatorController.setOutputRange(-1, 1);
		elevatorController.setPercentTolerance(1);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new MoveElevator());
    }
    public void setElevatorSpeed(double speed) {
    	elevatorMotor1.set(speed);
    	elevatorMotor2.set(speed);
    }
    public void setElevatorPIDSpeed(double speed) {
    	if (Math.abs(speed) < 0.05) {  //effectively no speed -> maintain current pos
    		if (!maintainingPos) {
    			maintainingPos = true;
    			elevatorController.setSetpoint(this.elevatorEncoder.getDistance());
    		}
    		if (!elevatorController.isEnabled()) {
    			elevatorController.enable();
    		}
    		
    	} else {
    		if (elevatorController.isEnabled()) {
    			elevatorController.disable();
    		}
    		this.setElevatorSpeed(speed);
    	}
    }
    public void setElevatorSpeed(double upTrigger, double downTrigger) {
    	if (upTrigger <= 0.05 && downTrigger <= 0.05 || upTrigger > 0.05 && downTrigger > 0.05) { //if neither are pressed, or if both are pressed, do nothing
    		this.setElevatorPIDSpeed(0); //Do nothing.
    	} else if (upTrigger > 0.05) {
    		this.setElevatorPIDSpeed(upTrigger);
    	} else if (downTrigger > 0.05) {
    		this.setElevatorPIDSpeed(-downTrigger);
    	}
    }
}

