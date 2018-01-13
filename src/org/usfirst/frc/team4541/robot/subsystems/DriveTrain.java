package org.usfirst.frc.team4541.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.commands.TankDriveWithJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class DriveTrain extends Subsystem {
	private WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(3);
	private WPI_TalonSRX rearLeftMotor = new WPI_TalonSRX(1);
	private WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(4);
	private WPI_TalonSRX rearRightMotor = new WPI_TalonSRX(2);
	
	private SpeedControllerGroup leftMotors  = new SpeedControllerGroup(frontLeftMotor,  rearLeftMotor);
	private SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, rearRightMotor);
	
	public static PowerDistributionPanel panel = new PowerDistributionPanel(0);

	private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

	public DriveTrain() {
		super();
		
	}

	/**
	 * When no other command is running let the operator drive around using the
	 * PS3 joystick.
	 */
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());
	}

	/**
	 * Tank style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double forward, double rotate) {
		drive.arcadeDrive(forward, rotate);
	}

	/**
	 * @param joy
	 *            The ps3 style joystick to use to drive tank style.
	 */
	public void drive(Joystick joy) {
		this.drive(-joy.getRawAxis(4), joy.getRawAxis(1));
	}

}
