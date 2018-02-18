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
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.commands.DriveForward;
import org.usfirst.frc.team4541.robot.commands.DrivePath;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.commands.ManualMoveElevator;
import org.usfirst.frc.team4541.robot.commands.PIDMoveElevator;
import org.usfirst.frc.team4541.robot.commands.TurnToAngle;
import org.usfirst.frc.team4541.robot.subsystems.Climber;
import org.usfirst.frc.team4541.robot.subsystems.CompressorSystem;
import org.usfirst.frc.team4541.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4541.robot.subsystems.Elevator;
import org.usfirst.frc.team4541.robot.subsystems.Intake;
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
	// Robot wide sensors
	public static AHRS gyro;
	public static LidarInterface lidar;
	public static UltrasonicInterface ultrasonic;

	// subsystems
	public static Elevator elevator;
	public static DriveTrain drivetrain;
	public static Intake intake;
	public static CompressorSystem compressor;

	@Deprecated // trackball will be removed soon in favor of encoders on wheels
	public static TrackBall trackball;
	public static Climber climber;

	SendableChooser<Command> m_chooser = new SendableChooser<>();
	public static String[] autoList = {"straight", "middle switch"};
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		gyro = new AHRS(SPI.Port.kMXP);

		trackball = new TrackBall();

		lidar = new LidarInterface(0x62, 0); // TODO: make sure that 0x62 is the
												// correct i2c address
		lidar.beginInfiniteFastContinuous(); // lidar starts reading at 50hz
												// indefinitely

		ultrasonic = new UltrasonicInterface();
		ultrasonic.setUltrasonicsEnabled(true, false, false, false);

		drivetrain = new DriveTrain();
		oi = new OI();
		elevator = new Elevator();

		intake = new Intake();
		compressor = new CompressorSystem();
		climber = new Climber();

		oi.initPostSubsystemButtons();
		SmartDashboard.putStringArray("Auto List",autoList);

		// CameraServer.getInstance().startAutomaticCapture(0);
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
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		String autoSelected = SmartDashboard.getString("Auto List", "straight");
		switch (autoSelected) {
		case "straight":
			new CommandGroup() {
				protected void initialize() {
					this.addSequential(new DriveForward(4));					
				}
			}.start();
			break;
		case "middle switch":
			new CommandGroup() {
				protected void initialize() {
					if (gameData.charAt(0) == 'L' || gameData.charAt(0) == 'l') {
						this.addSequential(new DrivePath(PATHS.LEFT_SWITCH));	
					} else {
						this.addSequential(new DrivePath(PATHS.RIGHT_SWITCH));	
					}
					this.addParallel(new PIDMoveElevator(Constants.maxElevatorHeight / 4));
					this.addSequential(new EjectCube());
				}
			}.start();
		}

		// FieldPositionHelper.beginIntegration();
		// new DrivePath(PATHS.DEFAULT_PATH).start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		drivetrain.drive(0.2, 0);
		SmartDashboard.putNumber("Left Speed: ", drivetrain.getLeftTalon().getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Speed: ", drivetrain.getRightTalon().getSelectedSensorVelocity(0));
	}

	@Override
	public void teleopInit() {
		// FieldPositionHelper.stopIntegration();
		compressor.setCompressorState(true);
		// make sure to .cancel() auto commands when this starts
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Angle: ", Robot.gyro.getYaw());
		SmartDashboard.putNumber("Left Encoder: ", drivetrain.getLeftTalon().getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder: ", drivetrain.getRightTalon().getSelectedSensorPosition(0));
		SmartDashboard.putNumber("ElevatorPos: ", elevator.elevatorMotor.getSelectedSensorPosition(0));

		Scheduler.getInstance().run();

		// oi.processDPadInput(); //runs elevator commands when D-Pad is pressed
		// oi.updateElevatorControl(); //checks to make sure that triggers are
		// pressed
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
