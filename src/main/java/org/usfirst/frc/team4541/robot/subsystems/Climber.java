package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.MoveClimber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	public WPI_TalonSRX climberMotor1 = new WPI_TalonSRX(RobotMap.climberMotor1);
	public WPI_TalonSRX climberMotor2 = new WPI_TalonSRX(RobotMap.climberMotor2);
	private DoubleSolenoid climberSol = new DoubleSolenoid(4,5);
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new MoveClimber());
	}
	public void setClimberVelocity(double velocity) {
		climberMotor1.set(velocity);
		climberMotor2.set(velocity);
	}
	public void extendClimber() {
		climberSol.set(Value.kForward);
	}
	public void retractClimber() {
		climberSol.set(Value.kReverse);
	}
    
}

