package org.usfirst.frc.team4541.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.TankDriveWithJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
	private WPI_TalonSRX leftMotor1  = new WPI_TalonSRX(RobotMap.leftDriveMotor1);
	private WPI_TalonSRX leftMotor2  = new WPI_TalonSRX(RobotMap.leftDriveMotor2);
	
	private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(RobotMap.rightDriveMotor1);
	private WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(RobotMap.rightDriveMotor2);
	
	public Encoder leftWheelEncoder  = new Encoder(0, 1, false, EncodingType.k4X);
	public Encoder rightWheelEncoder = new Encoder(2, 3, false, EncodingType.k4X);
	
	private SpeedControllerGroup leftMotors  = new SpeedControllerGroup(leftMotor1,  leftMotor2);
	private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotor1, rightMotor2);
	
//	public static PowerDistributionPanel panel = new PowerDistributionPanel(0);

	private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

	public DriveTrain() {
		super();
		leftMotor2.follow(leftMotor1);
		rightMotor2.follow(rightMotor1);
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
		drive.curvatureDrive(forward, rotate, true);
	}
	
	public void setVelocitySetpoint(double lSpeed, double rSpeed) {
		leftMotor1.set(ControlMode.Velocity, lSpeed);
		leftMotor2.set(ControlMode.Velocity, lSpeed);
//		rightMotor1.set(ControlMode.Velocity, rSpeed);
//		rightMotor2.set(ControlMode.Velocity, rSpeed);
	}
	public boolean isVelocityControlMode() {
		return this.leftMotor1.getControlMode() == ControlMode.Velocity && this.leftMotor2.getControlMode() == ControlMode.Velocity && this.rightMotor1.getControlMode() == ControlMode.Velocity && this.rightMotor2.getControlMode() == ControlMode.Velocity;
	}
	/**
	 * @param joy
	 *            The ps3 style joystick to use to drive tank style.
	 */
	public void drive(Joystick joy) {
		this.drive(joy.getRawAxis(1), joy.getRawAxis(4));
	}
	
	public double getDistanceMoved() {
		return (leftWheelEncoder.getDistance() + rightWheelEncoder.getDistance()) / 2;
	}
	
	public WPI_TalonSRX getRightTalon() {
		return this.rightMotor1;
	}
	
	public WPI_TalonSRX getLeftTalon() {
		return this.leftMotor1;
	}

}
