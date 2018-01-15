package org.usfirst.frc.team4541.robot;

import edu.wpi.first.wpilibj.I2C;

//The class that handles I2C communication between LIDARv2 and roborio
//Ported to Roborio code from Arduino LIDAR lite v2 library https://github.com/PulsedLight3D/LIDARLite_v2_Arduino_Library/blob/master/LIDARLite/LIDARLite.cpp
public class LidarInterface {
	private static I2C i2c;

	public LidarInterface(int configuration, int address) {
		i2c = new I2C(I2C.Port.kOnboard, address);
		switch (configuration) {
		case 0:
			i2c.write(0x00, 0x00); // Default configuration
			break;

		case 1:
			i2c.write(0x04, 0x00);// Set aquisition count to 1/3 default value,
									// faster reads, slightly
									// noisier values
			break;

		case 2:
			i2c.write(0x1c, 0x20);// Low noise, low sensitivity: Pulls decision
									// criteria higher
									// above the noise, allows fewer false
									// detections, reduces
									// sensitivity
			break;

		case 3:
			i2c.write(0x1c, 0x60); // High noise, high sensitivity: Pulls
									// decision criteria into the
									// noise, allows more false detections,
									// increases sensitivity
			break;
		}

	}

	public void beginContinuous(boolean modePinLow, int interval, int numberOfReadings) {
		// Register 0x45 sets the time between measurements. 0xc8 corresponds to
		// 10Hz
		// while 0x13 corresponds to 100Hz. Minimum value is 0x02 for proper
		// operation.
		i2c.write(0x45, interval);
		// Set register 0x04 to 0x20 to look at "NON-default" value of velocity
		// scale
		// If you set bit 0 of 0x04 to "1" then the mode pin will be low when
		// done
		if (modePinLow) {
			i2c.write(0x04, 0x21);
		} else {
			i2c.write(0x04, 0x20);
		}
		// Set the number of readings, 0xfe = 254 readings, 0x01 = 1 reading and
		// 0xff = continuous readings
		i2c.write(0x11, numberOfReadings);
		// Initiate reading distance
		i2c.write(0x00, 0x04);
	}

	public void beginInfiniteFastContinuous() { // queries Continuous readings
												// forever, as fast as possible
		i2c.write(0x45, 0x02); // smallest time between measurements
		i2c.write(0x04, 0x20); // modePinLow doesn't really matter for our
								// purposes
		i2c.write(0x11, 0xff); // continuous readings
		i2c.write(0x00, 0x04); // Initiate reading distance
	}

	public int distanceContinuous() { // Gets distance when lidar is in
										// continuous mode
		byte[] distanceArray = { 0x00, 0x00 }; // Array to store high and low
												// bytes of distance
		i2c.read(0x8f, 2, distanceArray); // Read two bytes from register 0x8f.
											// (See autoincrement note above)
		int distance = (distanceArray[0] << 8) + distanceArray[1]; // Shift high
																	// byte and
																	// add to
																	// low byte
		return (distance);
	}

}
