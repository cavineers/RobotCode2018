package org.usfirst.frc.team4541.superProfiling;

import org.usfirst.frc.team4541.robot.Robot;

/**
 * THIS CLASS IS A MODIFIED VERSION OF THE PATHFOLLOWER CLASS MADE BY TEAM 254
 * GITHUB LINK: https://github.com/Team254/FRC-2017-Public/blob/master/src/com/team254/lib/util/motion/ProfileFollower.java
 * 
 * A controller for tracking a profile generated to attain a MotionProfileGoal. The controller uses feedforward on
 * acceleration, velocity, and position; proportional feedback on velocity and position; and integral feedback on
 * position.
 */
public class SuperFollower {
    protected double mKp;
    protected double mKi;
    protected double mKv;
    protected double mKffv;
    protected double mKffa;

    protected double mMinOutput = -1;
    protected double mMaxOutput = 1;
    protected double mLatestPosError;
    protected double mLatestVelError;
    protected double mTotalError;

    protected Setpoint mLatestSetpoint = null;
    protected Setpoint mPrevSetpoint   = null;
    protected SuperRobotSide mPrevRobotState   = null;
    protected SuperRobotSide mLatestRobotState = null;
    protected Double mLatestStartTime = null;
    protected Double mPrevStartTime = null;
    protected ProfileState currentProfileState;
   
    public enum ProfileState {
    	kInvalidState,
    	kRunning,
    	kFinished
    }
    
    /**
     * Create a new ProfileFollower.
     * 
     * @param kp
     *            The proportional gain on position error.
     * @param ki
     *            The integral gain on position error.
     * @param kv
     *            The proportional gain on velocity error (or derivative gain on position error).
     * @param kffv
     *            The feedforward gain on velocity. Should be 1.0 if the units of the profile match the units of the
     *            output.
     * @param kffa
     *            The feedforward gain on acceleration.
     */
    public SuperFollower(double kp, double ki, double kv, double kffv, double kffa) {
        resetProfile();
        setGains(kp, ki, kv, kffv, kffa);
    }

    public void setGains(double kp, double ki, double kv, double kffv, double kffa) {
        mKp = kp;
        mKi = ki;
        mKv = kv;
        mKffv = kffv;
        mKffa = kffa;
    }

    /**
     * Completely clear all state related to the current profile (min and max outputs are maintained).
     */
    public void resetProfile() {
        mTotalError = 0.0;
        currentProfileState = ProfileState.kInvalidState;
        mLatestSetpoint = null;
        mLatestPosError = 0;
        mLatestVelError = 0;
        resetSetpoint();
    }

    /**
     * Reset just the setpoint. This means that the latest_state provided to update() will be used rather than feeding
     * forward the previous setpoint the next time update() is called. This almost always forces a MotionProfile update,
     * and may be warranted if tracking error gets very large.
     */
    public void resetSetpoint() {
        mLatestSetpoint = null;
    }

    public void resetIntegral() {
        mTotalError = 0.0;
    }

    /**
     * Update the setpoint and apply the control gains to generate a control output.
     * 
     * @param latest_state
     *            The latest *actual* state, used only for feedback purposes (unless this is the first iteration or
     *            reset()/resetSetpoint() was just called, in which case this is the new start state for the profile).
     * @param t
     *            The timestamp for which the setpoint is desired.
     * @return An output that reflects the control output to apply to achieve the new setpoint.
     */
    public synchronized double update(Setpoint latestSetpoint, SuperRobotSide currentRobotState, Double startTime) {
    	mPrevSetpoint = mLatestSetpoint;
    	mLatestSetpoint = latestSetpoint;
    	
    	mPrevRobotState = mLatestRobotState;
    	mLatestRobotState = currentRobotState;
    	
    	mPrevStartTime = mLatestStartTime;
    	mLatestStartTime = startTime;
    	
        if (mPrevRobotState == null) {
            mPrevRobotState = currentRobotState;
        }
        if (mPrevSetpoint == null) {
        	mPrevSetpoint = latestSetpoint;
        }
        if (mPrevStartTime == null) {
        	mPrevStartTime = startTime;
        }
        
        System.out.println(latestSetpoint.position);  //TODO: REMOVE IF SUPER PROFILING WORKS
        final double dt = Math.max(0.0, mLatestStartTime - mPrevStartTime);

        // Update error.
        mLatestPosError = mLatestSetpoint.position - currentRobotState.pos;
        mLatestVelError = mLatestSetpoint.velocity - currentRobotState.vel;

        // Calculate the feedforward and proportional terms.
        double output = mKp * mLatestPosError + mKv * mLatestVelError + mKffv * mLatestSetpoint.velocity
                + mKffa * mLatestSetpoint.accel;
        if (output >= mMinOutput && output <= mMaxOutput) {
            // Update integral.
            mTotalError += mLatestPosError * dt;
            output += mKi * mTotalError;
        } else {
            // Reset integral windup.
            mTotalError = 0.0;
        }
        // Clamp to limits.
        output = Math.max(mMinOutput, Math.min(mMaxOutput, output));

        return output;
    }

    public void setMinOutput(double min_output) {
        mMinOutput = min_output;
    }

    public void setMaxOutput(double max_output) {
        mMaxOutput = max_output;
    }

    public double getPosError() {
        return mLatestPosError;
    }

    public double getVelError() {
        return mLatestVelError;
    }

    /**
     * We are finished the profile when the final setpoint has been generated. Note that this does not check whether we
     * are anywhere close to the final setpoint, however.
     * 
     * @return True if the final setpoint has been generated for the current goal.
     */
    public boolean isFinishedProfile() {
        return mLatestSetpoint.isLast && this.onTarget();
    }

    /**
     * We are on target if our actual state achieves the goal (where the definition of achievement depends on the goal's
     * completion behavior).
     * 
     * @return True if we have actually achieved the current goal.
     */
    public boolean onTarget() {
//        if (mLatestSetpoint == null) {
//            return false;
//        }
//        // For the options that don't achieve the goal velocity exactly, also count any instance where we have passed
//        // the finish line.
//        final double goal_to_start = mGoal.pos() - mInitialState.pos();
//        final double goal_to_actual = mGoal.pos() - mLatestActualState.pos();
//        final boolean passed_goal_state = Math.signum(goal_to_start) * Math.signum(goal_to_actual) < 0.0;
//        return mGoal.atGoalState(mLatestActualState)
//                || (mGoal.completion_behavior() != CompletionBehavior.OVERSHOOT && passed_goal_state);
        return true;
    }
}