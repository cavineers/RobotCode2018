package org.usfirst.frc.team4541.superProfiling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class SuperProfile {
	// Tells where robot should be at a given time in a path
	HashMap<Long, CombinedSetpoint> timedPath = new HashMap<Long, CombinedSetpoint>();
	String errorPath = null;
	
	public SuperProfile(String pathName) {
		ArrayList<CombinedSetpoint> setpoints = loadCombinedSetpointFromFiles(pathName + "_left_detailed", pathName + "_right_detailed");
		Long time = (long) 0;
		for (CombinedSetpoint point : setpoints) {
			timedPath.put(time, point);
			time += point.dt;
		}
	}

	public CombinedSetpoint getCombinedSetpointForTime(long time) { // time in ms
		return timedPath.get(this.getClosestElementInArray(timedPath.keySet().toArray(new Long[timedPath.size()]), time));
	}
	
	public boolean hasValidPath() {
		return timedPath != null && timedPath.size() > 0;
	}
	
	//if it can't find the csv file, it stores the path it tried
	public String getErrorPath() { 
		return errorPath;
	}
	
	public long getClosestElementInArray(Long[] array, long target) {
		//uses binary search to find the point with a time closest to the requested time
		int low = 0;
		int high = array.length - 1;
		while (true) {
			int mid = (low + high) / 2;
			if (array[mid] < target) {
				low = mid + 1;
			}
			else if (array[mid] > target) {
				high = mid - 1;
			} else if (array[mid] == target) {
				return array[mid];
			}
			
			if(low == high || Math.abs(high - low) == 1) {
				//narrowed it down to two numbers - pick the closest
				if (Math.abs(array[low] - target) < Math.abs(array[high] - target)) {
					return array[low];
				} else {
					return array[high];
				}
			}
		}
	}

	public ArrayList<CombinedSetpoint> loadCombinedSetpointFromFiles(String leftPath, String rightPath) {
		ArrayList<CombinedSetpoint> setpointList = new ArrayList<CombinedSetpoint>();
		ArrayList<Setpoint> leftList = new ArrayList<Setpoint>();
		ArrayList<Setpoint> rightList = new ArrayList<Setpoint>();

		// load the setpoints for the left wheel into the setpoint array
		File file = new File("file://" + leftPath);
		try {
			Scanner inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				// values[0] - dt
				// values[1] - x pos
				// values[2] - y pos
				// values[3] - position
				// values[4] - velocity
				// values[5] - acceleration
				// values[6] - jerk
				// values[7] - heading
				leftList.add(new Setpoint(!inputStream.hasNext(), (int) (Double.parseDouble(values[0]) * 1000),
						Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
						Double.parseDouble(values[7])));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errorPath = file.getAbsolutePath();
		}

		// load the setpoints for the right wheel into the setpoint array
		file = new File("file://" + rightPath);
		try {
			Scanner inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				rightList.add(new Setpoint(!inputStream.hasNext(), (int) (Double.parseDouble(values[0]) * 1000),
						Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
						Double.parseDouble(values[7])));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errorPath = file.getAbsolutePath();
		}

		for (int i = 0; i < Math.min(leftList.size(), rightList.size()); i++) {
			setpointList.add(new CombinedSetpoint(leftList.get(i), rightList.get(i)));
		}
		return setpointList;
	}

}
