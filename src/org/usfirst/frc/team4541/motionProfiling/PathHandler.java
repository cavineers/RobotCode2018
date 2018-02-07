package org.usfirst.frc.team4541.motionProfiling;

public class PathHandler {
	public enum PATHS {
		DEFAULT_PATH,
		RIGHT_TURN
	}
	public static double[][] defaultPathRightStraight = new double[][] {
			  { 0.009015, 0.144233, 50 },
			  { 0.023438, 0.288467, 50 },
			  { 0.045073, 0.4327, 50 },
			  { 0.07392, 0.576933, 50 },
			  { 0.109978, 0.721166, 50 },
			  { 0.153248, 0.8654, 50 },
			  { 0.203729, 1.009633, 50 },
			  { 0.261423, 1.153866, 50 },
			  { 0.326328, 1.298099, 50 },
			  { 0.398444, 1.442333, 50 },
			  { 0.477773, 1.586566, 50 },
			  { 0.564313, 1.730799, 50 },
			  { 0.658064, 1.875032, 50 },
			  { 0.759028, 2.019266, 50 },
			  { 0.867202, 2.163499, 50 },
			  { 0.982589, 2.307732, 50 },
			  { 1.105187, 2.451965, 50 },
			  { 1.234997, 2.596199, 50 },
			  { 1.372019, 2.740432, 50 },
			  { 1.516252, 2.884665, 50 },
			  { 1.667697, 3.028898, 50 },
			  { 1.826354, 3.173132, 50 },
			  { 1.990419, 3.281307, 50 },
			  { 2.154693, 3.285481, 50 },
			  { 2.313976, 3.185655, 50 },
			  { 2.466256, 3.045596, 50 },
			  { 2.611324, 2.901363, 50 },
			  { 2.74918, 2.757129, 50 },
			  { 2.879825, 2.612896, 50 },
			  { 3.003258, 2.468663, 50 },
			  { 3.11948, 2.32443, 50 },
			  { 3.228489, 2.180196, 50 },
			  { 3.330288, 2.035963, 50 },
			  { 3.424874, 1.89173, 50 },
			  { 3.512249, 1.747497, 50 },
			  { 3.592412, 1.603263, 50 },
			  { 3.665364, 1.45903, 50 },
			  { 3.731103, 1.314797, 50 },
			  { 3.789632, 1.170563, 50 },
			  { 3.840948, 1.02633, 50 },
			  { 3.885053, 0.882097, 50 },
			  { 3.921946, 0.737864, 50 },
			  { 3.951628, 0.59363, 50 },
			  { 3.974097, 0.449397, 50 },
			  { 3.989356, 0.305164, 50 },
			  { 3.997402, 0.160931, 50 },
			  { 3.999831, 0.048581, 50 },
			  { 4.00004, 0.004174, 50 },
			  { 4.00004, 0, 50 }
	};
	public static double[][] defaultPathLeftStraight = new double[][] {
		  { 0.009015, 0.144233, 50 },
		  { 0.023438, 0.288467, 50 },
		  { 0.045073, 0.4327, 50 },
		  { 0.07392, 0.576933, 50 },
		  { 0.109978, 0.721166, 50 },
		  { 0.153248, 0.8654, 50 },
		  { 0.203729, 1.009633, 50 },
		  { 0.261423, 1.153866, 50 },
		  { 0.326328, 1.298099, 50 },
		  { 0.398444, 1.442333, 50 },
		  { 0.477773, 1.586566, 50 },
		  { 0.564313, 1.730799, 50 },
		  { 0.658064, 1.875032, 50 },
		  { 0.759028, 2.019266, 50 },
		  { 0.867202, 2.163499, 50 },
		  { 0.982589, 2.307732, 50 },
		  { 1.105187, 2.451965, 50 },
		  { 1.234997, 2.596199, 50 },
		  { 1.372019, 2.740432, 50 },
		  { 1.516252, 2.884665, 50 },
		  { 1.667697, 3.028898, 50 },
		  { 1.826354, 3.173132, 50 },
		  { 1.990419, 3.281307, 50 },
		  { 2.154693, 3.285481, 50 },
		  { 2.313976, 3.185655, 50 },
		  { 2.466256, 3.045596, 50 },
		  { 2.611324, 2.901363, 50 },
		  { 2.74918, 2.757129, 50 },
		  { 2.879825, 2.612896, 50 },
		  { 3.003258, 2.468663, 50 },
		  { 3.11948, 2.32443, 50 },
		  { 3.228489, 2.180196, 50 },
		  { 3.330288, 2.035963, 50 },
		  { 3.424874, 1.89173, 50 },
		  { 3.512249, 1.747497, 50 },
		  { 3.592412, 1.603263, 50 },
		  { 3.665364, 1.45903, 50 },
		  { 3.731103, 1.314797, 50 },
		  { 3.789632, 1.170563, 50 },
		  { 3.840948, 1.02633, 50 },
		  { 3.885053, 0.882097, 50 },
		  { 3.921946, 0.737864, 50 },
		  { 3.951628, 0.59363, 50 },
		  { 3.974097, 0.449397, 50 },
		  { 3.989356, 0.305164, 50 },
		  { 3.997402, 0.160931, 50 },
		  { 3.999831, 0.048581, 50 },
		  { 4.00004, 0.004174, 50 },
		  { 4.00004, 0, 50 }
	};
	
	
	public static double[][] defaultPathLeftTurn = new double[][] {
		
		{ 0.003704, 0.148148, 50 },
		  { 0.015999, 0.245898, 50 },
		  { 0.036499, 0.410007, 50 },
		  { 0.065218, 0.574382, 50 },
		  { 0.102175, 0.739136, 50 },
		  { 0.147394, 0.904387, 50 },
		  { 0.200907, 1.070261, 50 },
		  { 0.262752, 1.236893, 50 },
		  { 0.332973, 1.404429, 50 },
		  { 0.411625, 1.57303, 50 },
		  { 0.498768, 1.742872, 50 },
		  { 0.594476, 1.914154, 50 },
		  { 0.698831, 2.087092, 50 },
		  { 0.811927, 2.261933, 50 },
		  { 0.933875, 2.438949, 50 },
		  { 1.064797, 2.618447, 50 },
		  { 1.204836, 2.800766, 50 },
		  { 1.35415, 2.986284, 50 },
		  { 1.51292, 3.175412, 50 },
		  { 1.68135, 3.368596, 50 },
		  { 1.859665, 3.566301, 50 },
		  { 2.048115, 3.769, 50 },
		  { 2.246972, 3.977137, 50 },
		  { 2.456526, 4.19108, 50 },
		  { 2.677078, 4.411046, 50 },
		  { 2.908928, 4.636993, 50 },
		  { 3.152352, 4.868477, 50 },
		  { 3.402913, 5.011222, 50 },
		  { 3.655907, 5.059887, 50 },
		  { 3.911111, 5.104065, 50 },
		  { 4.168205, 5.141882, 50 },
		  { 4.426781, 5.171521, 50 },
		  { 4.686351, 5.19141, 50 },
		  { 4.946372, 5.200422, 50 },
		  { 5.206274, 5.198024, 50 },
		  { 5.465492, 5.18436, 50 },
		  { 5.723503, 5.160225, 50 },
		  { 5.979851, 5.126954, 50 },
		  { 6.234163, 5.086248, 50 },
		  { 6.486161, 5.039968, 50 },
		  { 6.733734, 4.951458, 50 },
		  { 6.972292, 4.771146, 50 },
		  { 7.199386, 4.541885, 50 },
		  { 7.415306, 4.318402, 50 },
		  { 7.620354, 4.100956, 50 },
		  { 7.814827, 3.889466, 50 },
		  { 7.999009, 3.683641, 50 },
		  { 8.173163, 3.483074, 50 },
		  { 8.337528, 3.287305, 50 },
		  { 8.492321, 3.095861, 50 },
		  { 8.637735, 2.908284, 50 },
		  { 8.773942, 2.724142, 50 },
		  { 8.901094, 2.543037, 50 },
		  { 9.019325, 2.364608, 50 },
		  { 9.128751, 2.188529, 50 },
		  { 9.229476, 2.014508, 50 },
		  { 9.321591, 1.842283, 50 },
		  { 9.405172, 1.671623, 50 },
		  { 9.480288, 1.502316, 50 },
		  { 9.546996, 1.334176, 50 },
		  { 9.605348, 1.167031, 50 },
		  { 9.655384, 1.000728, 50 },
		  { 9.697141, 0.835125, 50 },
		  { 9.730645, 0.670092, 50 },
		  { 9.75592, 0.505505, 50 },
		  { 9.772983, 0.34125, 50 },
		  { 9.781844, 0.177217, 50 },
		  { 9.784225, 0.047622, 50 },
		  { 9.784225, 0, 50 }
	};
	
	public static double[][] defaultPathRightTurn = new double[][] {
		 { 0.003704, 0.148148, 50 },
		  { 0.013631, 0.198546, 50 },
		  { 0.030168, 0.330734, 50 },
		  { 0.0533, 0.462655, 50 },
		  { 0.08301, 0.594197, 50 },
		  { 0.119272, 0.725241, 50 },
		  { 0.162055, 0.855662, 50 },
		  { 0.211322, 0.985326, 50 },
		  { 0.267026, 1.114085, 50 },
		  { 0.329115, 1.241778, 50 },
		  { 0.397526, 1.368229, 50 },
		  { 0.472188, 1.49324, 50 },
		  { 0.553018, 1.616593, 50 },
		  { 0.63992, 1.738042, 50 },
		  { 0.732786, 1.857313, 50 },
		  { 0.831491, 1.974102, 50 },
		  { 0.935894, 2.088065, 50 },
		  { 1.045835, 2.198827, 50 },
		  { 1.161134, 2.305974, 50 },
		  { 1.281587, 2.409059, 50 },
		  { 1.406968, 2.507616, 50 },
		  { 1.537026, 2.60117, 50 },
		  { 1.67149, 2.689274, 50 },
		  { 1.810068, 2.771559, 50 },
		  { 1.952458, 2.847804, 50 },
		  { 2.098361, 2.918047, 50 },
		  { 2.247497, 2.98273, 50 },
		  { 2.396898, 2.988023, 50 },
		  { 2.543862, 2.939283, 50 },
		  { 2.688614, 2.895034, 50 },
		  { 2.831472, 2.857154, 50 },
		  { 2.972845, 2.827465, 50 },
		  { 3.113222, 2.80754, 50 },
		  { 3.253148, 2.798513, 50 },
		  { 3.393193, 2.800915, 50 },
		  { 3.533924, 2.814603, 50 },
		  { 3.675863, 2.83878, 50 },
		  { 3.819468, 2.872107, 50 },
		  { 3.965112, 2.91288, 50 },
		  { 4.113074, 2.959233, 50 },
		  { 4.262363, 2.985779, 50 },
		  { 4.410163, 2.956009, 50 },
		  { 4.554618, 2.889093, 50 },
		  { 4.695436, 2.816376, 50 },
		  { 4.832317, 2.737604, 50 },
		  { 4.96496, 2.652861, 50 },
		  { 5.093082, 2.562439, 50 },
		  { 5.216419, 2.466749, 50 },
		  { 5.334732, 2.366253, 50 },
		  { 5.447803, 2.261425, 50 },
		  { 5.555439, 2.152725, 50 },
		  { 5.657468, 2.040586, 50 },
		  { 5.753739, 1.925407, 50 },
		  { 5.844116, 1.807549, 50 },
		  { 5.928483, 1.687339, 50 },
		  { 6.006737, 1.56507, 50 },
		  { 6.078787, 1.441002, 50 },
		  { 6.144555, 1.31537, 50 },
		  { 6.203975, 1.188383, 50 },
		  { 6.256986, 1.06023, 50 },
		  { 6.30354, 0.931079, 50 },
		  { 6.343594, 0.801087, 50 },
		  { 6.377114, 0.670394, 50 },
		  { 6.404071, 0.539132, 50 },
		  { 6.424442, 0.407422, 50 },
		  { 6.438211, 0.275381, 50 },
		  { 6.445367, 0.143118, 50 },
		  { 6.44729, 0.038472, 50 },
		  { 6.44729, 0, 50 }
		
	};
	
	public static double[][] getRightPointsForPath(PATHS path) {
		switch (path) {
		case DEFAULT_PATH:
			return defaultPathRightStraight;
		case RIGHT_TURN:
			return defaultPathRightTurn;
		}
		return null;
	}
	public static double[][] getLeftPointsForPath(PATHS path) {
		switch (path) {
		case DEFAULT_PATH:
			return defaultPathLeftTurn;
		case RIGHT_TURN:
			return defaultPathLeftTurn;
		}
		return null;
	}
	public static double ftPerSecToRPM(double feetPerSec) {
		return ftToRotations(feetPerSec)*60;
	}
	public static double ftToRotations(double feet) {
		return feet/Constants.wheelCircumference*Constants.kDriveGearRatio;
	}
}
