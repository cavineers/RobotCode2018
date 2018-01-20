package org.usfirst.frc.team4541.robot;

import java.text.DecimalFormat;
import java.util.Arrays;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter;
import edu.wpi.first.wpilibj.hal.DIOJNI;
import edu.wpi.first.wpilibj.hal.RelayJNI;

//The class that handles SPI communication between arduino due and rio
public class UltrasonicInterface {
	private static I2C i2c;
	
	public UltrasonicInterface() {
		i2c = new I2C(I2C.Port.kOnboard, 4);
	}
	public void setUltrasonicsEnabled(boolean e1, boolean e2, boolean e3, boolean e4) {
		byte[] sendBytes = {0x00, 0x00}; //The first byte is a zero to tell the arduino we are sending an enable/disable command
		if (e1) {						 //Second byte is in format 00004321 where 4, 3, 2, and 1 are 0 if the sensor should be disabled or 1 if it should be enabled
			sendBytes[1] += 1;
		}
		if (e2) {
			sendBytes[1]+=2;
		}
		if (e3) {
			sendBytes[1]+=4;
		}
		if (e4) {
			sendBytes[1]+=8;
		}
		byte[] receivedData = {0x00};
		i2c.transaction(sendBytes, sendBytes.length, receivedData, receivedData.length);
		if (receivedData.length != 1 || !(receivedData[0] == 1)) {
			System.out.println("ERROR INITIALIZING ULTRASONIC SYSTEM");
		}
	}
	public double getUltrasonic(int i) {
		byte[] sendBytes = {0x01, (byte)i};
		byte[] receivedData = {0x00, 0x00, 0x00};
		System.out.println("RECEIVED: " + receivedData);
		i2c.transaction(sendBytes, sendBytes.length, receivedData, receivedData.length);
		
		int distance = (receivedData[0] & 0xFF) << 8;
		distance = distance | (receivedData[1] & 0xFF);
		if (receivedData[2] == 1) {
			System.out.println("ULTRASONIC ERROR: BAD ULTRASONIC DATA RECEIVED!!");
			return -1;
		}

		return distance/100.0;

	}

}
