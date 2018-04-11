package org.usfirst.frc.team4541.superProfiling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class SuperProfile {
	// TODO: THINK OF A BETTER WAY TO HANDLE PATHS
	// maybe a path parent class with children responsible for loading data from
	// csv into hashmap
	// then path.getCombinedSetpointForTime(time)?
	public SuperProfile() {

	}

	public abstract CombinedSetpoint getCombinedSetpointForTime(double time);

	public static ArrayList<CombinedSetpoint> loadCombinedSetpointFromFiles(String path) {
		
		File file = new File(path);
		try {
			Scanner inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				//values[0]; //heading
				//values[1]; //x pos
				//values[2]; //y pos
				//values[3]; //position
				//values[4]; //velocity
				//values[5]; //acceleration
				//values[6]; //jerk
				//values[7]; //heading
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// This prints out the working directory
		System.out.println("Present Project Directory : " + System.getProperty("user.dir"));

	}

}
