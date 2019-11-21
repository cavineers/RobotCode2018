package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.command.Subsystem;


import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.TankDriveWithJoystick;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class DriveTrain extends Subsystem {
	public WPI_TalonSRX leftMotor1  = new WPI_TalonSRX(RobotMap.leftDriveMotor1);
	public WPI_TalonSRX leftMotor2  = new WPI_TalonSRX(RobotMap.leftDriveMotor2);
	
	public WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(RobotMap.rightDriveMotor1);
	public WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(RobotMap.rightDriveMotor2);
	private DoubleSolenoid sol;
	
//	private SpeedControllerGroup leftMotors  = new SpeedControllerGroup(leftMotor1,  leftMotor2);
//	private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotor1, rightMotor2);
	
//	public static PowerDistributionPanel panel = new PowerDistributionPanel(0);

	private DifferentialDrive drive = new DifferentialDrive(leftMotor1, rightMotor1);

	public DriveTrain() {
		super();
		drive.setSafetyEnabled(false);
		leftMotor2.follow(leftMotor1);
		rightMotor2.follow(rightMotor1);
		leftMotor1.setInverted(true);
		leftMotor2.setInverted(true);
		
		rightMotor1.setInverted(true);
		rightMotor2.setInverted(true);
		
		sol = new DoubleSolenoid(RobotMap.PCM, 2, 3);
		// originally 0,1
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
    public boolean isSolenoidOpen() {
    	return sol.get() == DoubleSolenoid.Value.kReverse;
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
	public void profileDrive(double left, double right) {
		leftMotor1.set(left);
		leftMotor2.set(left);
		rightMotor1.set(right);
		rightMotor2.set(right);
	}
	/**
	 * @param joy
	 *            The xbox style joystick to use to drive tank style.
	 */
	public void drive(Joystick joy) {
		this.drive(Robot.oi.addDeadZone(-joy.getRawAxis(1)), Robot.oi.addDeadZone(joy.getRawAxis(4)));
	}
	
	
	public WPI_TalonSRX getRightTalon() {
		return this.rightMotor1;
	}
	public WPI_TalonSRX getRightSlaveTalon() {
		return this.rightMotor2;
	}
	
	public WPI_TalonSRX getLeftTalon() {
		return this.leftMotor1;
	}
	public WPI_TalonSRX getLeftSlaveTalon() {
		return this.leftMotor2;
	}

}
