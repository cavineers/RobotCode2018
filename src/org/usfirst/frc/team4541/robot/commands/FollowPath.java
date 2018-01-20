package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Constants;
import org.usfirst.frc.team4541.robot.FieldPositionHelper;
import org.usfirst.frc.team4541.robot.Robot;
import com.team254.frc2017.RobotState;

import com.team254.frc2017.Kinematics;
import com.team254.lib.util.control.Lookahead;
import com.team254.lib.util.control.Path;
import com.team254.lib.util.control.PathFollower;
import com.team254.lib.util.math.RigidTransform2d;
import com.team254.lib.util.math.Rotation2d;
import com.team254.lib.util.math.Translation2d;
import com.team254.lib.util.math.Twist2d;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class FollowPath extends Command {
    private Path mCurrentPath = null;
    private PathFollower mPathFollower;
    private Path mPath;
    public FollowPath(Path path) {
    	requires(Robot.drivetrain);
    	mPath = path;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setWantDrivePath(mPath, false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	this.updatePathFollower(Timer.getFPGATimestamp());
    }
    /**
     * Configures the drivebase to drive a path. Used for autonomous driving
     * 
     * @see Path
     */
    public synchronized void setWantDrivePath(Path path, boolean reversed) {
//        configureTalonsForSpeedControl();
        RobotState.getInstance().resetDistanceDriven();
        mPathFollower = new PathFollower(path, reversed,
                new PathFollower.Parameters(
                        new Lookahead(Constants.kMinLookAhead, Constants.kMaxLookAhead,
                                Constants.kMinLookAheadSpeed, Constants.kMaxLookAheadSpeed),
                        Constants.kInertiaSteeringGain, Constants.kPathFollowingProfileKp,
                        Constants.kPathFollowingProfileKi, Constants.kPathFollowingProfileKv,
                        Constants.kPathFollowingProfileKffv, Constants.kPathFollowingProfileKffa,
                        Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel,
                        Constants.kPathFollowingGoalPosTolerance, Constants.kPathFollowingGoalVelTolerance,
                        Constants.kPathStopSteeringDistance));
//        mDriveControlState = DriveControlState.PATH_FOLLOWING;
        mCurrentPath = path;
      
    }
    
    private void updateVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
        if (Robot.drivetrain.isVelocityControlMode()) {
            final double max_desired = Math.max(Math.abs(left_inches_per_sec), Math.abs(right_inches_per_sec));
            final double scale = max_desired > Constants.kDriveHighGearMaxSetpoint
                    ? Constants.kDriveHighGearMaxSetpoint / max_desired : 1.0;
            Robot.drivetrain.setVelocitySetpoint(inchesPerSecondToRpm(left_inches_per_sec * scale), inchesPerSecondToRpm(right_inches_per_sec * scale));
        } else {
            System.out.println("Hit a bad velocity control state");
            Robot.drivetrain.setVelocitySetpoint(0, 0);
        }
    }
    /**
     * Called periodically when the robot is in path following mode. Updates the path follower with the robots latest
     * pose, distance driven, and velocity, the updates the wheel velocity setpoints.
     */
    private void updatePathFollower(double timestamp) {
    	
        Twist2d command = mPathFollower.update(timestamp, FieldPositionHelper.getLatestPosition(),
                RobotState.getInstance().getDistanceDriven(), RobotState.getInstance().getPredictedVelocity().dx);
        if (!mPathFollower.isFinished()) {
            Kinematics.DriveVelocity setpoint = Kinematics.inverseKinematics(command);
            updateVelocitySetpoint(setpoint.left, setpoint.right);
        } else {
            updateVelocitySetpoint(0, 0);
        }
    }
    private static double inchesPerSecondToRpm(double inches_per_second) {
        return inchesToRotations(inches_per_second) * 60;
    }
    private static double inchesToRotations(double inches) {
        return inches / (Constants.kDriveWheelDiameterInches * Math.PI);
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
