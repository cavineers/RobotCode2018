/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4541.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CAN addresses for talons
	//Drive Motors
	public static final int leftDriveMotor1  = 0;
	public static final int leftDriveMotor2  = 1;
	public static final int rightDriveMotor1 = 2;
	public static final int rightDriveMotor2 = 3;
    //Intake Motors
	public static final int intakeMotor1 = 4;
	public static final int intakeMotor2 = 5;
    //Elevator Motors
	public static final int elevatorMotor1 = 6;
	public static final int elevatorMotor2 = 7;
	
}
