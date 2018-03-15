package org.usfirst.frc.team4541.robot;


public class ElevatorConstants {


	
	//Elevator Class
	public static final double maxElevatorHeight  = 42000; //TODO: set actual max height (native units)
	public static final double switchHeight       = 12000; //24 inches times 500 (pulses per 100ms)
	public static final double avgScaleHeight     = 36000; //72 inches times 500
	public static final double minElevatorHeight = 0; //TODO: set actual min height (native units)
	public static final double pulsesPerInch = 500; 
	public static final double maxSpeed = 3000; //pulses per 100ms
	public static final double twoInches = 2*pulsesPerInch;
	public static final double maxA = 800; //pulses per 100ms ^2

	
}