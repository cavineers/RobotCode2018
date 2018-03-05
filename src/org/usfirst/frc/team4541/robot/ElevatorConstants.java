package org.usfirst.frc.team4541.robot;


public class ElevatorConstants {


	
	//Elevator Class
	public static final double triggerCoefficient = 4000; //Set actual trigger coeff. 
	public static final double maxElevatorHeight  = 42000; //TODO: set actual max height (native units)
	public static final double switchHeight       = 12000; //24 inches times 500 (pulses per 100ms)
	public static final double avgScaleHeight     = 36000; //72 inches times 500
	public static final double minElevatorHeight = 0; //TODO: set actual min height (native units)
	public static final double pulsesPerInch = 500; 
	public static final double upperDangerZone = .8*(maxElevatorHeight);
	public static final double lowerDangerZone = .2*(maxElevatorHeight);
	public static final double maxSpeed = 1000; //pulses per 100ms
	public static final double twoInches = 2*pulsesPerInch;
	
}