package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.MoveIntake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public WPI_TalonSRX intakeMotor1 = new WPI_TalonSRX(RobotMap.intakeMotor1);
	public WPI_TalonSRX intakeMotor2 = new WPI_TalonSRX(RobotMap.intakeMotor2);
	public DoubleSolenoid sol = new DoubleSolenoid(RobotMap.PCM, 2, 3);
	
    public void initDefaultCommand() {
    	this.setDefaultCommand(new MoveIntake());
    }
    
    public void setIntakeSpeed(double speed) {
    	intakeMotor1.set(-speed);
    	intakeMotor2.set(speed);
    }
	
    public void IntakePiston() {
    	intakeMotor1.setInverted(false);
    	intakeMotor2.setInverted(false);
    	
    	intakeMotor1.configContinuousCurrentLimit(10, 0);
    	intakeMotor1.configPeakCurrentLimit(40, 0);
    	intakeMotor1.configPeakCurrentDuration(500, 0);
    	intakeMotor1.enableCurrentLimit(true);
    	
    	intakeMotor2.configContinuousCurrentLimit(10, 0);
    	intakeMotor2.configPeakCurrentLimit(40, 0);
    	intakeMotor2.configPeakCurrentDuration(500, 0);
    	intakeMotor2.enableCurrentLimit(true);
    }
    
    public void setSolenoidOpen(boolean state){
    	if (state) {
    		sol.set(DoubleSolenoid.Value.kForward);
    	} else {
    		sol.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    public boolean isSolenoidOpen() {
    	return (sol.get() == DoubleSolenoid.Value.kForward);
    }
    
    public boolean getSolenoidState() {
    	if (sol.get() == DoubleSolenoid.Value.kForward) return true;
    	return false;
    }
    
}

