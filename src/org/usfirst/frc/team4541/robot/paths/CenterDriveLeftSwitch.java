package org.usfirst.frc.team4541.robot.paths;

import java.util.ArrayList;

import com.team254.frc2017.paths.PathBuilder;
import com.team254.frc2017.paths.PathBuilder.Waypoint;
import com.team254.frc2017.paths.PathContainer;
import com.team254.lib.util.control.Path;
import com.team254.lib.util.math.RigidTransform2d;
import com.team254.lib.util.math.Rotation2d;
import com.team254.lib.util.math.Translation2d;


public class CenterDriveLeftSwitch implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(0,155,0,0));
        sWaypoints.add(new Waypoint(70,155,40,60));
        sWaypoints.add(new Waypoint(70,219,20,60));
        sWaypoints.add(new Waypoint(121,219,0,60));

        return PathBuilder.buildPathFromWaypoints(sWaypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(0, 155), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":0,"y":155},"speed":0,"radius":0,"comment":""},{"position":{"x":70,"y":155},"speed":60,"radius":40,"comment":""},{"position":{"x":70,"y":219},"speed":60,"radius":20,"comment":""},{"position":{"x":121,"y":219},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: UntitledPath
}