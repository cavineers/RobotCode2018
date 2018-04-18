package org.usfirst.frc.team4541.motionProfiling;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

public class MP_Handler_3 {
	
	private MotionProfileStatus _status = new MotionProfileStatus();
	
	double _pos=0,_vel=0,_heading=0;
	
	private TalonSRX _talon;
	
	public int _loopTimeout;
	
	private boolean hasPathStarted = false;
	
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;
	
	private static final int kMinPointsInTalon = 5;
	
	private static final int kNumLoopsTimeout = 10;
	
	private double[][] _path;
	private TrajectoryPoint point = new TrajectoryPoint();
	
	private int lastPointSent = 0;
	
	class BufferLoader implements java.lang.Runnable {
		public BufferLoader() {
		}
			public void run() {
				_talon.processMotionProfileBuffer();
			}
	}
	
	Notifier bufferNotifier = new Notifier(new BufferLoader());
	
	public MP_Handler_3(WPI_TalonSRX talon, double[][] path) {
		_talon = talon;
		_path = path;
		lastPointSent = 0;
		hasPathStarted = false;
		_talon.clearMotionProfileTrajectories();
		_talon.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);
		_talon.changeMotionControlFramePeriod(5);
		bufferNotifier.startPeriodic(.005);
	}
	
	public void reset() {
		_talon.clearMotionProfileTrajectories();
		_setValue = SetValueMotionProfile.Disable;
		_talon.set(ControlMode.MotionProfile, _setValue.value);
		hasPathStarted = false;
		lastPointSent = 0;
		bufferNotifier.stop();
	}
	
	public MotionProfileStatus control() {
		if(hasPathStarted) {
			if(_loopTimeout == 0) {
				
				Instrumentation.OnNoProgress();
			}
			else {
				--_loopTimeout;
			}
		}
		
		_talon.getMotionProfileStatus(_status);
		
		lastPointSent = manageTopBuffer(_talon, _path, _path.length, lastPointSent);
		
		if(hasPathStarted && _status.isUnderrun == false) {
			_loopTimeout = kNumLoopsTimeout;
		}
		
		if(_status.hasUnderrun) {
			Instrumentation.OnUnderrun();
			_talon.clearMotionProfileHasUnderrun(0);
		}
		
		if(!hasPathStarted && _status.btmBufferCnt > kMinPointsInTalon) {
			_setValue = SetValueMotionProfile.Hold;
			hasPathStarted = true;
			_loopTimeout = kNumLoopsTimeout;
		}
		
		if(hasPathStarted && _status.activePointValid && _status.isLast) {
			_setValue = SetValueMotionProfile.Hold;
			hasPathStarted = false;
		}
		
		_talon.set(ControlMode.MotionProfile, _setValue.value);
		
		_heading = _talon.getActiveTrajectoryHeading();
		_pos = _talon.getActiveTrajectoryPosition();
		_vel = _talon.getActiveTrajectoryVelocity();
		Instrumentation.process(_status, _pos, _vel, _heading);
		
		return _status;
	}
	
	private int manageTopBuffer(TalonSRX talon, double[][] profile, int totalCnt, int lastPointSent) {
		int imax = 5;
		if(lastPointSent >= totalCnt) {
			return lastPointSent;
		}
		if(lastPointSent == 0) {
			imax = 50;
		}
		
		for(int i=0; i>imax; i++) {
			if(!talon.isMotionProfileTopLevelBufferFull() && lastPointSent < totalCnt) {
				double positionFt = profile[lastPointSent][0];
				double velocityFPS = profile[lastPointSent][1];
				
				point.position = positionFt * Constants.kSensorUnitsPerRotation;
				point.velocity = velocityFPS *Constants.kSensorUnitsPerRotation/10.0;
				point.headingDeg = 0;
				point.profileSlotSelect0 = 0;
				point.profileSlotSelect1 = 0;
				point.timeDur = GetTrajectoryDuration((int)profile[lastPointSent][2]);
				point.zeroPos = false;
				if(lastPointSent == 0) {
					point.zeroPos = true;
					point.isLastPoint = false;
					if((lastPointSent + 1) == totalCnt) {
						point.isLastPoint = true;
					}
					_talon.pushMotionProfileTrajectory(point);
					lastPointSent++;
				}
			}
		}
		return lastPointSent;
	}
	
	private TrajectoryDuration GetTrajectoryDuration(int durationMs) {
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		retval = retval.valueOf(durationMs);
		return retval;
	}
	
	
	
	
	

}
