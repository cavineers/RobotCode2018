package org.usfirst.frc.team4541.superProfiling;

public class SuperHeadingAdjuster {
	double totalPositionOffset = 0;
	public SuperHeadingAdjuster() {
		
	}
	public CombinedSetpoint getAdjustedCombinedSetpointForHeading(CombinedSetpoint cspoint, double headingError) {
		double turnVel = SuperConstants.kRotationP * (headingError/180.0) ; //SuperConstants.kRotationP * (angle_difference / 180.0);

		cspoint.lVel += turnVel * SuperConstants.kV;
		cspoint.rVel -= turnVel * SuperConstants.kV;
		
		//integrate the difference in velocity over time to modify the position points of the profile
		totalPositionOffset += (turnVel * ((double)cspoint.dt)/1000.0);  
		
		cspoint.lPos += totalPositionOffset;
		cspoint.rPos -= totalPositionOffset;
		
		System.out.println("vel change: " + turnVel + "\ttotal position change: " + totalPositionOffset);
		
		return cspoint;
	}
	
	//Turns angle to between 180 and -180 degrees
	public static double standardize(double angle) {
		while (angle <= -180 || angle >= 180) {
			if (angle > 0)
				angle -= 360;
			else
				angle += 360;
		}
		return angle;
	}
}
