package org.usfirst.frc.team4541.robot.subsystems;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import org.usfirst.frc.team4541.robot.commands.MoveIntake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
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
	public boolean isCurrentLimited = false;
	private double highCurrentStartTime = -1;
	
	private static final double maxCurrent      = 30; //30A 
	private static final double highCurrentTimeAllowed = 0.5; //half a second
	
    public void initDefaultCommand() {
    	this.setDefaultCommand(new MoveIntake());
    }
    
    public void setIntakeSpeed(double speed) {
    	intakeMotor1.set(-speed);
//    	intakeMotor1.configContinuousCurrentLimit(10, 0);
//    	intakeMotor1.configPeakCurrentLimit(80, 0);
//    	intakeMotor1.configPeakCurrentDuration(500, 0);
//    	intakeMotor1.enableCurrentLimit(true);
    	
    	intakeMotor2.set(speed);
//    	intakeMotor2.configContinuousCurrentLimit(10, 0);
//    	intakeMotor2.configPeakCurrentLimit(80, 0);
//    	intakeMotor2.configPeakCurrentDuration(500, 0);
//    	intakeMotor2.enableCurrentLimit(true);
     }
	
    public void IntakePiston() {
    	intakeMotor1.setInverted(false);
    	intakeMotor2.setInverted(false);
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
    	if (sol.get() == DoubleSolenoid.Value.kReverse) return true;
    	return false;
    }
    
    public void updateCurrentLimit() {
    	if (!this.isCurrentLimited) return;
    	if (Robot.oi.currentTriggerSetting == TRIG_MODE.INTAKE) {
    		this.isCurrentLimited = false;
    		return;
    	}
    	if (this.intakeMotor1.getOutputCurrent() > maxCurrent || this.intakeMotor1.getOutputCurrent() > maxCurrent) {
    		if (this.highCurrentStartTime == -1) { 
    			//Grabber just started exceeding current limit; log time
    			this.highCurrentStartTime = Timer.getFPGATimestamp();
    		} else if (Timer.getFPGATimestamp() - this.highCurrentStartTime > highCurrentTimeAllowed) {
				//Grabber exceeded max current for longer than allowed; shut off timer
				Robot.intake.setIntakeSpeed(0);
	    		Robot.intake.isCurrentLimited = false;
    		}
    	} else {
    		//if neither of the grabber motors are exceeding the current limit, set currentStartTime to -1
    		this.highCurrentStartTime = -1;
    	}
    }
}

