package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.motionProfiling.Constants;

public class SuperConstants {
	//path following constants
	public static double kP = 10;
	public static double kI = 0.04;
	public static double kV = 90; //much higher than the poof's value of 0.02
	
	public static double mKffv = 0.86;
	public static double mKffa = 0.05; //untested - taken from cheezypoof's code
	
	public static double kRotationP = 0.04; //same as used in drive at angle
	
}
