package org.usfirst.frc.team4541.motionProfiling;

public class PathHandler {
	public enum PATHS {
		DEFAULT_PATH
	}
	public static double[][] defaultPathRight = new double[][] {
		
	};
	public static double[][] defaultPathLeft = new double[][] {
		
	};
	public static double[][] getRightPointsForPath(PATHS path) {
		switch (path) {
		case DEFAULT_PATH:
			return defaultPathRight;
		}
		return null;
	}
	public static double[][] getLeftPointsForPath(PATHS path) {
		switch (path) {
		case DEFAULT_PATH:
			return defaultPathLeft;
		}
		return null;
	}
}
