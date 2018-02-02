package org.usfirst.frc.team4541.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.command.Subsystem;


import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotMap;
import org.usfirst.frc.team4541.robot.commands.TankDriveWithJoystick;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



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
	private DoubleSolenoid sol;
	
//	private SpeedControllerGroup leftMotors  = new SpeedControllerGroup(leftMotor1,  leftMotor2);
//	private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotor1, rightMotor2);
	
//	public static PowerDistributionPanel panel = new PowerDistributionPanel(0);

	private DifferentialDrive drive = new DifferentialDrive(leftMotor1, rightMotor1);

	public DriveTrain() {
		super();
		this.configTalons();
		leftMotor2.follow(leftMotor1);
		rightMotor2.follow(rightMotor1);
		sol = new DoubleSolenoid(5,0,1); //TODO: SET CANID
	}

	/**
	 * When no other command is running let the operator drive around using the
	 * PS3 joystick.
	 */
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());
	}

    public void setSolenoidOpen(boolean state){
    	if (state) {
    		sol.set(DoubleSolenoid.Value.kReverse);
    	} else {
    		sol.set(DoubleSolenoid.Value.kForward);
    	}
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
	/**
	 * @param joy
	 *            The xbox style joystick to use to drive tank style.
	 */
	public void drive(Joystick joy) {
		this.drive(Robot.oi.addDeadZone(-joy.getRawAxis(1)), Robot.oi.addDeadZone(joy.getRawAxis(4)));
	}
	
	// modifies the input of a joystick axis by adding dead zones and squaring
	// the inputs, intended to be used with XBOX controllers or other
	// controllers with many predefined axes
/*	public double addDeadZone(double input) {
		if (Math.abs(input) <= .05)
			input = 0;
		else if (input < 0)
			input = -Math.pow(input, 2);
		else
			input = Math.pow(input, 2);
		return input;
	}*/
	
	public WPI_TalonSRX getRightTalon() {
		return this.rightMotor1;
	}
	public WPI_TalonSRX getRightSlaveTalon() {
		return this.rightMotor2;
	}
	
	public WPI_TalonSRX getLeftTalon() {
		return this.leftMotor1;
	}
	public void configTalons() {
		rightMotor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		rightMotor1.setSensorPhase(true); /* keep sensor and motor in phase */
		rightMotor1.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

		rightMotor1.config_kF(0, 0.6, Constants.kTimeoutMs);
		rightMotor1.config_kP(0, 0.1, Constants.kTimeoutMs);
		rightMotor1.config_kI(0, 0.0, Constants.kTimeoutMs);
		rightMotor1.config_kD(0, 0.0, Constants.kTimeoutMs);

		/* Our profile uses 50ms timing */
		rightMotor1.configMotionProfileTrajectoryPeriod(50, Constants.kTimeoutMs); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		rightMotor1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 50, Constants.kTimeoutMs);
		
		leftMotor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		leftMotor1.setSensorPhase(true); /* keep sensor and motor in phase */
		leftMotor1.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);
		leftMotor1.config_kF(0, 0.6, Constants.kTimeoutMs);
		leftMotor1.config_kP(0, 0.1, Constants.kTimeoutMs);
		leftMotor1.config_kI(0, 0.0, Constants.kTimeoutMs);
		leftMotor1.config_kD(0, 0.0, Constants.kTimeoutMs);
		
		leftMotor1.configMotionProfileTrajectoryPeriod(50, Constants.kTimeoutMs); 
		leftMotor1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 50, Constants.kTimeoutMs);
	}

	public double getDistanceMoved() {
		
		return (this.leftMotor1.getSelectedSensorPosition(0) + this.rightMotor1.getSelectedSensorPosition(0)) / 2.0;
	}
}
