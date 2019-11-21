/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Drive Motors 
	public static final int leftDriveMotor1  = 2;
	public static final int leftDriveMotor2  = 3;
	
	public static final int rightDriveMotor1 = 1;
	public static final int rightDriveMotor2 = 5;

	//Intake Motors
	public static final int intakeMotor1 = 7;
	public static final int intakeMotor2 = 6;
    //Elevator Motors
	public static final int elevatorMotor = 4;
	
	//PDP
	public static final int PDP = 8;
	//PCM
	public static final int PCM = 0;
	
	
}
