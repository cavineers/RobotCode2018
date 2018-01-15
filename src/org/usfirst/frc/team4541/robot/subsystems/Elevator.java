package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.MoveElevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	private WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(RobotMap.elevatorMotor1);
	private WPI_TalonSRX elevatorMotor2 = new WPI_TalonSRX(RobotMap.elevatorMotor2);
	public Encoder elevatorEncoder = new Encoder(4, 5, false, EncodingType.k4X);
	
    public void initDefaultCommand() {
    	setDefaultCommand(new MoveElevator());
    }
    public void setElevatorSpeed(double speed) {
    	elevatorMotor1.set(speed);
    	elevatorMotor2.set(speed);
    }
    public void setElevatorSpeed(double upTrigger, double downTrigger) {
    	if (upTrigger <= 0.05 && downTrigger <= 0.05 || upTrigger > 0.05 && downTrigger > 0.05) { //if neither are pressed, or if both are pressed, do nothing
    		return;
    	} else if (upTrigger > 0.05) {
    		this.setElevatorSpeed(upTrigger);
    	} else if (downTrigger > 0.05) {
    		this.setElevatorSpeed(-downTrigger);
    	}
    }
}

