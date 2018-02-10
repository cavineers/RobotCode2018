package org.usfirst.frc.team4541.motionProfiling;

public class Constants {

	/** which Talon on CANBus */
	public static final int kTalonID = 0;

	/**
	 * How many sensor units per rotation. Using CTRE Magnetic Encoder.
	 * 
	 * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
	 */
	public static final double kSensorUnitsPerRotation = 854.3; //pulses per foot 854.3 math; 876.7 experimentally, 848.18
	//4 times 64

	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;
	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 10;

	/**
	 * Base trajectory period to add to each individual trajectory point's
	 * unique duration. This can be set to any value within [0,255]ms.
	 */
	public static final int kBaseTrajPeriodMs = 0;

	/**
	 * Motor deadband, set to 1%.
	 */
	public static final double kNeutralDeadband = 0.01;
	
	/**
	 * Motion Profiling Stuff
	 */
// test robot
//	public static final double kDriveGearRatio = 13.46;
//	public static final double wheelDiameter = 7/12; 
//	public static final double wheelCircumference = Math.PI * wheelDiameter;
	
	public static final double kDriveGearRatio = 1;
	public static final double wheelDiameter = 6.2/12; 
	public static final double wheelCircumference = Math.PI * wheelDiameter;
	
	//Elevator Class
	public static final double triggerCoefficient = 100; //TODO: set actual trigger coeff. 
	public static final double maxElevatorHeight  = 1000; //TODO: set actual max height (native units)
	public static final double switchHeight       = 0; //TODO: set switch height (native units)
	public static final double scaleHeight        = 0; //TODO: set scale height (native units)
	public static final double maxScaleHeight     = 0; //TODO: set max scale height (native units)
}