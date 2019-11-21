package org.usfirst.frc.team4541.robot;

public class FieldPositionHelper {
	private static volatile double FieldXPos = 0;
	private static volatile double FieldYPos = 0;
	private static boolean running = true;
	public static Thread integrator = new Thread() {
		private double lastRobotDistanceMoved = 0;
		@Override
		public void run() {
			while (running) {
				double changeInDistance = Robot.drivetrain.getDistanceMoved() - lastRobotDistanceMoved;
				FieldXPos += changeInDistance * Math.sin(Robot.gyro.getAngle());
				FieldYPos += changeInDistance * Math.cos(Robot.gyro.getAngle());
				lastRobotDistanceMoved = Robot.drivetrain.getDistanceMoved();
				if (this.isInterrupted()) {
					 break;
				}
			}
		}
	};
	public static void beginIntegration() {
		integrator.start();
	}
	public static void stopIntegration() {
		running = false;
	}
	public static double getFieldXPos() {
		if (integrator.isAlive()) {
			return FieldXPos;
		}
		return Double.MIN_VALUE;
	}
	public static double getFieldYPos() {
		if (integrator.isAlive()) {
			return FieldYPos;
		}
		return Double.MIN_VALUE;
	}
	
}
