package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public WPI_TalonSRX intakeMotor1 = new WPI_TalonSRX(RobotMap.intakeMotor1);
	public WPI_TalonSRX intakeMotor2 = new WPI_TalonSRX(RobotMap.intakeMotor1);
	
    public void initDefaultCommand() {
    	
    }
    
    public void setIntakeSpeed(double speed) {
    	intakeMotor1.set(speed);
    	intakeMotor2.set(speed);
    }
    
}

