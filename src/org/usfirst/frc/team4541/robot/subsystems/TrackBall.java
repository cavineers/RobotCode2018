package org.usfirst.frc.team4541.robot.subsystems;

import java.nio.ByteBuffer;
import java.util.Arrays;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TrackBall  extends Subsystem {
	private static I2C i2c;
	@Override
	protected void initDefaultCommand() {
		
	}
	public TrackBall() {
		i2c = new I2C(I2C.Port.kOnboard, 4);
	}
	public double[] getPos() {
		try {
			byte[] byteArr = { 0 };
			byte[] recievedData = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
			i2c.transaction(byteArr, byteArr.length, recievedData, recievedData.length);
			
			byte[] XArray = Arrays.copyOfRange(recievedData, 0, 4);
			byte[] YArray = Arrays.copyOfRange(recievedData, 4, 8);
			
			long xPos = XArray[0] & 0xFF;
			xPos |= (XArray[1] << 8) & 0xFFFF;
		    xPos |= (XArray[2] << 16) & 0xFFFFFF;
		    xPos |= (XArray[3] << 24) & 0xFFFFFFFF;
		    
		    long yPos = YArray[0] & 0xFF;
		    yPos |= (YArray[1] << 8) & 0xFFFF;
		    yPos |= (YArray[2] << 16) & 0xFFFFFF;
		    yPos |= (YArray[3] << 24) & 0xFFFFFFFF;
			
		    double x = xPos/100.0;
		    double y = yPos/100.0;
			return new double[]{x,y};
		} catch (NullPointerException e) {
			return new double[]{};
		}

	}
}
