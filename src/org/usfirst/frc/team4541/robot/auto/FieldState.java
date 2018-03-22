package org.usfirst.frc.team4541.robot.auto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

public class FieldState {
	public enum FieldPos {
		RIGHT, LEFT, INVALID
	}
	public enum RobotPos {
		RIGHT, LEFT, MIDDLE, INVALID
	}
	FieldPos switchPos;
	FieldPos scalePos;
	FieldPos oppSwitchPos;
	RobotPos robotPos;
	public static DigitalInput left = new DigitalInput(1);
	public static DigitalInput middle = new DigitalInput(2);
	public static DigitalInput right = new DigitalInput(3);
	
	public FieldState(String fConfig, RobotPos robotPos) {
		this.configFieldStates(fConfig);
		this.robotPos = robotPos;
	}
	public Command getDesiredAuto() {
		if (robotPos == RobotPos.INVALID) {
			return new DriveToAutoLine(); 
		}
		if (robotPos == RobotPos.RIGHT) {
			if (this.scalePos == FieldPos.RIGHT) {
				return new RightScale();
			} else if (this.switchPos == FieldPos.RIGHT) {
				return new RightSwitchSide();
			} else {
				return new DriveToAutoLine();
			}
		} else if (robotPos == RobotPos.MIDDLE) {
			if (this.switchPos == FieldPos.RIGHT) {
				return new RightSwitchMP();
			} else if (this.switchPos == FieldPos.LEFT) {
				return new LeftSwitchMP();
			}
		} else if (robotPos == RobotPos.LEFT) {
			if (this.scalePos == FieldPos.LEFT) {
				return new LeftScale();
			} else if (this.switchPos == FieldPos.LEFT) {
				return new LeftSwitchSide();
			} else {
				return new DriveToAutoLine();
			}
		}
		return null;
	}
	
	public void configFieldStates(String fConfig) {
		if (fConfig.length() != 3) {
			System.out.println("INVALID FIELD STATE");
			switchPos = FieldPos.INVALID;
			scalePos = FieldPos.INVALID;
			oppSwitchPos = FieldPos.INVALID;
			return;
		}
		if (fConfig.charAt(0) == 'L' || fConfig.charAt(0) == 'l') {
			switchPos = FieldPos.LEFT;
		} else if (fConfig.charAt(0) == 'R' || fConfig.charAt(0) == 'r') {
			switchPos = FieldPos.RIGHT;
		} else {
			System.out.println("INVALID FIELD STATE");
			switchPos = FieldPos.INVALID;
		}
		
		if (fConfig.charAt(1) == 'L' || fConfig.charAt(1) == 'l') {
			scalePos = FieldPos.LEFT;
		} else if (fConfig.charAt(1) == 'R' || fConfig.charAt(1) == 'r') {
			scalePos = FieldPos.RIGHT;
		} else {
			System.out.println("INVALID FIELD STATE");
			scalePos = FieldPos.INVALID;
		}
		
		if (fConfig.charAt(2) == 'L' || fConfig.charAt(2) == 'l') {
			oppSwitchPos = FieldPos.LEFT;
		} else if (fConfig.charAt(2) == 'R' || fConfig.charAt(2) == 'r') {
			oppSwitchPos = FieldPos.RIGHT;
		} else {
			System.out.println("INVALID FIELD STATE");
			oppSwitchPos = FieldPos.INVALID;
		}
	}
	public static RobotPos getCurrentPositionFromJumpers() {
		boolean m = !middle.get(); //if the inputs are low - active, otherwise inactive
		boolean r = !left.get();
		boolean l = !right.get();
		
		if (m && !l && !r) { //middle is selected from jumpers
			return RobotPos.MIDDLE;
		} else if (r && !m && !l) { //left is selected
			return RobotPos.RIGHT;
		} else if (l && !m && !r) { //right is selected
			return RobotPos.LEFT;
		} else { //invalid combination
			return RobotPos.INVALID;
		}
	}
	public static String getNameFromRobotPos(RobotPos pos) {
		if (pos == RobotPos.MIDDLE) {
			return "MIDDLE";
		}
		if (pos == RobotPos.LEFT) {
			return "LEFT";
		}
		if (pos == RobotPos.RIGHT) {
			return "RIGHT";
		}
		return "INVALID";
	}
}
