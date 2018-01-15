/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4541.robot;

import org.usfirst.frc.team4541.robot.commands.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	enum SENSOR {
		ULTRASONIC_RIGHT, ULTRASONIC_LEFT, ULTRASONIC_FRONT, LIDAR_BACK, VISION_CUBE, VISION_TAPE, ENCODER_RIGHT_WHEELS, ENCODER_LEFT_WHEELS, ENCODER_ELEVATOR
	};

	Joystick joy = new Joystick(0);

	public OI() {
		// Create some buttons
		JoystickButton a_button = new JoystickButton(joy, 1);
		JoystickButton b_button = new JoystickButton(joy, 2);
		JoystickButton x_button = new JoystickButton(joy, 3);
		JoystickButton y_button = new JoystickButton(joy, 4);

		JoystickButton d_up = new JoystickButton(joy, 5);
		JoystickButton d_right = new JoystickButton(joy, 6);
		JoystickButton d_down = new JoystickButton(joy, 7);
		JoystickButton d_left = new JoystickButton(joy, 8);
		JoystickButton l2 = new JoystickButton(joy, 9);
		JoystickButton r2 = new JoystickButton(joy, 10);
		JoystickButton l1 = new JoystickButton(joy, 11);
		JoystickButton r1 = new JoystickButton(joy, 12);

		a_button.whenPressed(new TurnToAngle(90));
		x_button.whenPressed(new DriveToPosAtAngle(0, 30));
	}

	public Joystick getJoystick() {
		return joy;
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
					return 0;
				case ENCODER_LEFT_WHEELS:
					return 0;	
				case ENCODER_ELEVATOR:
					return 0;
				}
				return 0;
			}
		};
	}
}
