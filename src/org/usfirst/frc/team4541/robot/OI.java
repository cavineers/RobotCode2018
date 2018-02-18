/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4541.robot;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.usfirst.frc.team4541.motionProfiling.Constants;
import org.usfirst.frc.team4541.motionProfiling.PathHandler;
import org.usfirst.frc.team4541.robot.commands.ChangeTriggerMode;
import org.usfirst.frc.team4541.robot.commands.DrivePath;
import org.usfirst.frc.team4541.robot.commands.DriveToPosAtAngle;
import org.usfirst.frc.team4541.robot.commands.EjectCube;
import org.usfirst.frc.team4541.robot.commands.ManualMoveElevator;
import org.usfirst.frc.team4541.robot.commands.PIDMoveElevator;
import org.usfirst.frc.team4541.robot.commands.ShiftGear;
import org.usfirst.frc.team4541.robot.commands.ToggleIntake;
import org.usfirst.frc.team4541.robot.commands.TurnToAngle;
import org.usfirst.frc.team4541.robot.commands.setIntakeContracted;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import java.util.Hashtable;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public enum SENSOR {
		ENCODER_RIGHT_WHEELS, ENCODER_LEFT_WHEELS, ENCODER_ELEVATOR
	};
	public enum TRIG_MODE {
		ELEVATOR, INTAKE, CLIMBER
	}
	public TRIG_MODE currentTriggerSetting;

	public static Joystick joy = new Joystick(0);
	public static JoystickButton a_button = new JoystickButton(joy, 1);
	public static JoystickButton b_button = new JoystickButton(joy, 2);
	public static JoystickButton x_button = new JoystickButton(joy, 3);
	public static JoystickButton y_button = new JoystickButton(joy, 4);

	public static JoystickButton l_bump = new JoystickButton(joy, 5);
	public static JoystickButton r_bump = new JoystickButton(joy, 6);
	public static JoystickButton left_middle = new JoystickButton(joy, 7);
	public static JoystickButton right_middle = new JoystickButton(joy, 8);
	public static JoystickButton left_stick = new JoystickButton(joy, 9);
	public static JoystickButton right_stick = new JoystickButton(joy, 10);
	
	public OI() {
		// Create some buttons
		
		r_bump.whenPressed(new ShiftGear(false));
		l_bump.whenPressed(new ShiftGear(true));
	}
	public void processDPadInput() {
		switch (joy.getPOV()) {
		case 0: {
			//Top; move to scale max height
			clearElevatorPIDCommands();
			new PIDMoveElevator(Constants.maxElevatorHeight).start();
			break;
		}
		case 90: {
			//Right; move to switch
			clearElevatorPIDCommands();
			new PIDMoveElevator(Constants.switchHeight).start();
			break;
		}
		case 180: {
			//Bottom; move to bottom of elevator
			clearElevatorPIDCommands();
			new PIDMoveElevator(0).start();
			break;
		}
		case 270: {
			//Left; move to average height of elevator
			clearElevatorPIDCommands();
			new PIDMoveElevator(Constants.avgScaleHeight).start();
			break;
		}
		}
	}
	public void clearElevatorPIDCommands() {
		if (Robot.elevator.getCurrentCommand() instanceof PIDMoveElevator) {
			//prevents out of memory exceptions caused by many PID loops running at once
			PIDMoveElevator current = (PIDMoveElevator)Robot.elevator.getCurrentCommand();
			current.freePID();
			current.cancel();
		}
	}
	public void initPostSubsystemButtons() {
		x_button.whenPressed(new setIntakeContracted(true));
		b_button.whenPressed(new setIntakeContracted(false));
		y_button.whenPressed(new EjectCube());
		a_button.whenPressed(new ToggleIntake());
		
		left_middle.whenPressed(new Command() { //Toggle between elevator and climber
			 protected void initialize() { 
				if (Robot.oi.currentTriggerSetting == TRIG_MODE.ELEVATOR) {
					new ChangeTriggerMode(TRIG_MODE.CLIMBER);
				} else {
					new ChangeTriggerMode(TRIG_MODE.ELEVATOR);
				}
			 }
			@Override
			protected boolean isFinished() {
				return true;
			}
			
		});
		right_middle.whenPressed(new Command() { //Toggle between elevator and climber
			 protected void initialize() { 
				if (Robot.oi.currentTriggerSetting == TRIG_MODE.ELEVATOR) {
					new ChangeTriggerMode(TRIG_MODE.INTAKE);
				} else {
					new ChangeTriggerMode(TRIG_MODE.ELEVATOR);
				}
			 }
			@Override
			protected boolean isFinished() {
				return true;
			}
			
		});
		
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
				case ENCODER_RIGHT_WHEELS:
					return Robot.drivetrain.getRightTalon().getSelectedSensorPosition(0);
				case ENCODER_LEFT_WHEELS:
					return Robot.drivetrain.getLeftTalon().getSelectedSensorPosition(0);
				case ENCODER_ELEVATOR:
					return Robot.elevator.getElevatorPos();
				}
				return 0;
			}
		};
	}
	public double addDeadZone(double input) {
		if (Math.abs(input) <= .05)
			input = 0;
		else if (input < 0)
			input = -Math.pow(input, 2);
		else
			input = Math.pow(input, 2);
		return input;
	}
	public void updateElevatorControl() {
		double upTrig   = Robot.oi.getJoystick().getRawAxis(3);
    	double downTrig = Robot.oi.getJoystick().getRawAxis(2);
    	if (upTrig > 0.05 || downTrig > 0.05) {
    		if (!Robot.elevator.getCurrentCommandName().equals(Robot.elevator.getDefaultCommandName())) {
    			new ManualMoveElevator().start(); //Restart manual control if it had been suspended for PID control
    		}
    	}
	}
}
