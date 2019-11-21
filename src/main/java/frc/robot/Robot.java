/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.nio.file.Path;

import frc.robot.OI.TRIG_MODE;
import frc.robot.commands.elevator.ElevatorHome;
import frc.robot.commands.EjectCube;
import frc.robot.commands.setIntakeSpeed;
import frc.robot.subsystems.CompressorSystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI oi;
	// Robot wide sensors

	// subsystems
	public static Elevator elevator;
	public static DriveTrain drivetrain;
	public static Intake intake;
	public static CompressorSystem compressor;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		drivetrain = new DriveTrain();
		oi = new OI();
		elevator = new Elevator();

		intake = new Intake();
		compressor = new CompressorSystem();

		oi.initPostSubsystemButtons();
		
		
		UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture(0);

		cam0.setWhiteBalanceAuto();
		cam0.setExposureManual(50);
		cam0.setFPS(20);
		cam0.setResolution(330, (int)(330*(9.0/16.0)));
		
		UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture(1);
		
		cam1.setWhiteBalanceAuto();
		cam1.setExposureManual(50);
		cam1.setFPS(20);
		cam1.setResolution(330, (int)(330*(9.0/16.0)));
		
		SmartDashboard.putString("ENCODER STATUS", "DID NOT TEST");
				
		SmartDashboard.putBoolean("Grabber Contracted: ", !intake.getSolenoidState());
		SmartDashboard.putBoolean("Grabber Spinning: ", Math.abs(intake.intakeMotor1.get()) > 0.05 && oi.currentTriggerSetting != TRIG_MODE.INTAKE);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		elevator.getPIDMotorOutput().reset();
		elevator.getPIDVel().reset();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
//		currentAutoCommand.cancel();
		compressor.setCompressorState(true);
		new setIntakeSpeed(0).start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putBoolean("Grabber Contracted: ", !intake.getSolenoidState());
		SmartDashboard.putBoolean("Grabber Spinning: ", Math.abs(intake.intakeMotor1.get()) > 0.05 && oi.currentTriggerSetting != TRIG_MODE.INTAKE);
		Scheduler.getInstance().run();
		
		oi.processDPadInput(); //runs elevator commands when D-Pad is pressed
		intake.updateCurrentLimit();
//		SmartDashboard.putNumber("Grabber Current 1", intake.intakeMotor1.getOutputCurrent());
//		SmartDashboard.putNumber("Grabber Current 2", intake.intakeMotor2.getOutputCurrent());
		SmartDashboard.putBoolean("isLowGear: ", drivetrain.isSolenoidOpen());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	


}
