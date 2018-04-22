/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4541.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.motionProfiling.PathHandler.PATHS;
import org.usfirst.frc.team4541.robot.OI.TRIG_MODE;
import org.usfirst.frc.team4541.robot.auto.DriveToAutoLine;
import org.usfirst.frc.team4541.robot.auto.FieldState;
import org.usfirst.frc.team4541.robot.auto.RightSwitchPointTurn;
import org.usfirst.frc.team4541.robot.auto.TestEncoders;
import org.usfirst.frc.team4541.robot.auto.LeftSwitchPointTurn;
import org.usfirst.frc.team4541.robot.auto.RightOppScaleMP;
import org.usfirst.frc.team4541.robot.auto.RightOppScalePointTurn;
import org.usfirst.frc.team4541.robot.auto.FieldState.RobotPos;
import org.usfirst.frc.team4541.robot.auto.LeftOppScaleMP;
import org.usfirst.frc.team4541.robot.commands.auto.DriveForward;
import org.usfirst.frc.team4541.robot.commands.auto.DrivePath;
import org.usfirst.frc.team4541.robot.commands.auto.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.auto.OverrideAutoPosition;
import org.usfirst.frc.team4541.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.auto.disableAutoPositionOverride;
import org.usfirst.frc.team4541.robot.commands.elevator.ElevatorHome;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.subsystems.Climber;
import org.usfirst.frc.team4541.robot.subsystems.CompressorSystem;
import org.usfirst.frc.team4541.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4541.robot.subsystems.Elevator;
import org.usfirst.frc.team4541.robot.subsystems.Intake;
import org.usfirst.frc.team4541.superProfiling.SuperFollowPath;
import org.usfirst.frc.team4541.superProfiling.SuperRobotState;

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

	// subsystems
	public static Elevator elevator;
	public static DriveTrain drivetrain;
	public static Intake intake;
	public static CompressorSystem compressor;

	public static Climber climber;
	public static SendableChooser<FieldState.RobotPos> posChooser = new SendableChooser<>();
	public static FieldState fieldState;
	public static boolean isAutoPosOverridden = false;
	public static RobotPos overriddenRobotPos = RobotPos.INVALID;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		gyro = new AHRS(SPI.Port.kMXP);

		drivetrain = new DriveTrain();
		oi = new OI();
		elevator = new Elevator();

		intake = new Intake();
		compressor = new CompressorSystem();
		climber = new Climber();

		oi.initPostSubsystemButtons();
		
		posChooser = new SendableChooser<RobotPos>();
		posChooser.addDefault("Middle", FieldState.RobotPos.MIDDLE);
		posChooser.addObject("Right", FieldState.RobotPos.RIGHT);
		posChooser.addObject("Left", FieldState.RobotPos.LEFT);
		posChooser.addObject("Straight Override", RobotPos.INVALID);
		SmartDashboard.putData(posChooser);
		
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
		
		SmartDashboard.putData(new TestEncoders());
		SmartDashboard.putString("ENCODER STATUS", "DID NOT TEST");
		SmartDashboard.putData(new OverrideAutoPosition());
		SmartDashboard.putData(new disableAutoPositionOverride());
		
		SmartDashboard.putBoolean("Favors Scale", true);
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
		SmartDashboard.putString("Robot Position Setting: ", FieldState.getNameFromRobotPos(getAutoPos()));
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
	Command currentAutoCommand;
//	double currentTime = 0;
//	int counter = 0; 
	@Override
	public void autonomousInit() {
		drivetrain.configTalons();
//		fieldState = new FieldState(DriverStation.getInstance().getGameSpecificMessage(), getAutoPos(), SmartDashboard.getBoolean("Favors Scale",  true));
//		fieldState.getDesiredAuto().start();
		this.setPeriod(0.00005);
		currentAutoCommand = new SuperFollowPath("10ftTest_10ms"); //TODO: change back to a working auto before competition
		currentAutoCommand.start();
		
		//NOTE: currently with scheduler superProfiling updates ~25ms, with a for loop just updating the command
		//that drops to ~15ms.  TODO: Possibly use a custom scheduler to run commands in auto so profiling is faster.
		
//		while (this.isAutonomous()) {
//			((SuperFollowPath)currentAutoCommand).update();
////			Scheduler.getInstance().run();
//			if (counter % 40 == 0) {
//			SmartDashboard.putNumber("update interval", Timer.getFPGATimestamp() * 1000 - currentTime);
//			}	
//			currentTime = Timer.getFPGATimestamp() * 1000;
//			counter++;
//		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		 SmartDashboard.putNumber("Left Pos: ",
		 drivetrain.getLeftTalon().getSelectedSensorPosition(0));
		 SmartDashboard.putNumber("Right Pos: ",
		 drivetrain.getRightTalon().getSelectedSensorPosition(0));
//		if (this.isEnabled()) {
//			System.out.print(drivetrain.getRightTalon().get());
//			System.out.print(",");
//			System.out.print(drivetrain.getLeftTalon().get());
//			System.out.print(",");
//			System.out.print(drivetrain.getRightTalon().getSelectedSensorVelocity(0));
//			System.out.print(",");
//			System.out.print(drivetrain.getLeftTalon().getSelectedSensorVelocity(0));
//			System.out.println();
//		}
	}

	@Override
	public void teleopInit() {
//		currentAutoCommand.cancel();
		compressor.setCompressorState(true);
		this.setPeriod(0.002); //lower loop rate to default because profiling is no longer needed.
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
//		 SmartDashboard.putNumber("Angle: ", Robot.gyro.getYaw());
//		 SmartDashboard.putNumber("Left Encoder: ",
//		 drivetrain.getLeftTalon().getSelectedSensorPosition(0));
//		 SmartDashboard.putNumber("Right Encoder: ",
//		 drivetrain.getRightTalon().getSelectedSensorPosition(0));
		// SmartDashboard.putNumber("ElevatorPos: ",
		// elevator.elevatorMotor.getSelectedSensorPosition(0));
		// SmartDashboard.putNumber("Left Error: ",
		// drivetrain.getLeftTalon().getClosedLoopError(0));
		// SmartDashboard.putNumber("Right Error: ",
		// drivetrain.getRightTalon().getClosedLoopError(0));
		SmartDashboard.putBoolean("Grabber Contracted: ", !intake.getSolenoidState());
		SmartDashboard.putBoolean("Grabber Spinning: ", Math.abs(intake.intakeMotor1.get()) > 0.05 && oi.currentTriggerSetting != TRIG_MODE.INTAKE);
		Scheduler.getInstance().run();
		
		oi.processDPadInput(); //runs elevator commands when D-Pad is pressed
		intake.updateCurrentLimit();
		updateRumble();
//		SmartDashboard.putNumber("Grabber Current 1", intake.intakeMotor1.getOutputCurrent());
//		SmartDashboard.putNumber("Grabber Current 2", intake.intakeMotor2.getOutputCurrent());
		SmartDashboard.putBoolean("isLowGear: ", drivetrain.isSolenoidOpen());
		log();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	public RobotPos getAutoPos() {
		if (!isAutoPosOverridden) {
			return FieldState.getCurrentPositionFromJumpers();
		} else {
			return overriddenRobotPos;
		}
	}
	public static void updateRumble() {
		if (oi.currentTriggerSetting == TRIG_MODE.CLIMBER) {
			oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0.7);
			oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0.7);
		}
		else if (intake.getIntakeSpeed() > 0.2) { //grabber wheels are moving inwards
			oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0.5);
			oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0.5);
		} else {
			oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0.0);
			oi.getJoystick().setRumble(RumbleType.kLeftRumble, 0.0);
		}
	}
	private void log() {
		Robot.drivetrain.log();
	}
	
	public static SuperRobotState getCurrentRobotState() {
		return new SuperRobotState(drivetrain.getLeftVel(), drivetrain.getLeftPos(), drivetrain.getRightVel(), drivetrain.getRightPos(), gyro.getYaw());
	}
}
