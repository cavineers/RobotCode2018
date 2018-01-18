/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4541.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4541.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4541.robot.subsystems.Elevator;
import org.usfirst.frc.team4541.robot.subsystems.Intake;
import org.usfirst.frc.team4541.robot.subsystems.Ramps;
import org.usfirst.frc.team4541.robot.subsystems.TrackBall;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI oi;
	//Robot wide sensors
	public static AHRS gyro;
	public static LidarInterface lidar;
	public static UltrasonicInterface ultrasonic;
	
	//subsystems
	public static Elevator elevator;
	public static DriveTrain drivetrain;
	public static Ramps ramps;
	public static Intake intake;
	
	@Deprecated //trackball will be removed soon in favor of encoders on wheels
	public static TrackBall trackball;
	
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi =  new OI();
		
		gyro = new AHRS(SPI.Port.kMXP);
		
		trackball = new TrackBall();
		
		lidar = new LidarInterface(0x62, 0); //TODO: make sure that 0x62 is the correct i2c address
		lidar.beginInfiniteFastContinuous(); //lidar starts reading at 50hz indefinitely
		
		ultrasonic = new UltrasonicInterface();
		ultrasonic.setUltrasonicsEnabled(false, false, false, false);
		
		elevator = new Elevator();
		drivetrain = new DriveTrain();
		ramps = new Ramps();
		intake = new Intake();
		
		CameraServer.getInstance().startAutomaticCapture(0);
		SmartDashboard.putString("driver station message: ", DriverStation.getInstance().getGameSpecificMessage());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		FieldPositionHelper.beginIntegration();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		FieldPositionHelper.stopIntegration();
		//make sure to .cancel() auto commands when this starts
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
