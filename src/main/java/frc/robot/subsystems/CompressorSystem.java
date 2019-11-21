package frc.robot.subsystems;


import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class CompressorSystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Compressor comp;
	public static AnalogInput pressureTransducer = new AnalogInput(0);
	public CompressorSystem() {
		comp = new Compressor(RobotMap.PCM);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setCompressorState(boolean state) {
    	comp.setClosedLoopControl(state);
    }
    public boolean getCompressorClosedLoopState() {
    	return comp.getClosedLoopControl();
    }
    public boolean getCompressorState() {
    	return comp.enabled();
    }
}

