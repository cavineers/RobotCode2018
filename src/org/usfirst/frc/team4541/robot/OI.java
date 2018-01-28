/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4541.robot;

import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.robot.commands.DrivePath;
import org.usfirst.frc.team4541.robot.commands.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.commands.ToggleIntake;
import org.usfirst.frc.team4541.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public enum SENSOR {
		ULTRASONIC_RIGHT, ULTRASONIC_LEFT, ULTRASONIC_FRONT, LIDAR_BACK, VISION_CUBE, VISION_TAPE, ENCODER_RIGHT_WHEELS, ENCODER_LEFT_WHEELS, ENCODER_ELEVATOR
	};

	Joystick joy = new Joystick(0);
	XboxController cont = new XboxController(0);
	
	public OI() {
		// Create some buttons
		JoystickButton a_button = new JoystickButton(joy, 1);
		JoystickButton b_button = new JoystickButton(joy, 2);
		JoystickButton x_button = new JoystickButton(joy, 3);
		JoystickButton y_button = new JoystickButton(joy, 4);

		JoystickButton l_bump = new JoystickButton(joy, 5);
		JoystickButton r_bump = new JoystickButton(joy, 6);
		JoystickButton d_down = new JoystickButton(joy, 7);
		JoystickButton d_left = new JoystickButton(joy, 8);
		JoystickButton left_middle = new JoystickButton(joy, 9);
		JoystickButton right_middle = new JoystickButton(joy, 10);
		JoystickButton left_stick = new JoystickButton(joy, 11);
		JoystickButton right_stick = new JoystickButton(joy, 12);

		a_button.whenPressed(new EjectCube());
		b_button.whenPressed(new ToggleIntake());
		x_button.whenPressed(new TurnToAngle(90));
		y_button.whenPressed(new Command() {
		    protected void initialize() {
		    	Robot.drivetrain.getLeftTalon().setSelectedSensorPosition(0, 0, 0);
		    	Robot.drivetrain.getRightTalon().setSelectedSensorPosition(0, 0, 0);
		    }
			
			@Override
			protected boolean isFinished() {
				return true;
			}
			
		});
		l_bump.whenPressed(new DrivePath(PathHandler.PATHS.DEFAULT_PATH));
		
		
	}

	public Joystick getJoystick() {
		return joy;
	}
	public XboxController getController() {
		return cont;
	}
	public PIDSource getPIDSource(SENSOR sensor) {
		return new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				switch(sensor) {
				case ULTRASONIC_LEFT:
					return Robot.ultrasonic.getUltrasonic(2);
				case ULTRASONIC_RIGHT:
					return Robot.ultrasonic.getUltrasonic(3);
				case ULTRASONIC_FRONT:
					return Robot.ultrasonic.getUltrasonic(1);
				case LIDAR_BACK:
					return Robot.lidar.distanceContinuous();
				case VISION_CUBE:
					return 0;
				case VISION_TAPE:
					return 0;
				case ENCODER_RIGHT_WHEELS:
					return Robot.drivetrain.getRightTalon().getSelectedSensorPosition(0);
				case ENCODER_LEFT_WHEELS:
					return Robot.drivetrain.getLeftTalon().getSelectedSensorPosition(0);
				case ENCODER_ELEVATOR:
					return Robot.elevator.elevatorEncoder.getDistance();
				}
				return 0;
			}
		};
	}
}
