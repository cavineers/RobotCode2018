package com.team254.lib.util.drivers;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
/**
 * This class is a thin wrapper around the CANTalon that reduces CAN bus / CPU overhead by skipping duplicate set
 * commands. (By default the Talon flushes the Tx buffer on every set call).
 */
public class LazyCANTalon extends WPI_TalonSRX {
    protected double mLastSet = Double.NaN;
    protected ControlMode mLastControlMode = null;

    public LazyCANTalon(int deviceNumber, int controlPeriodMs, int enablePeriodMs) {
        super(deviceNumber, controlPeriodMs, enablePeriodMs);
    }

    public LazyCANTalon(int deviceNumber, int controlPeriodMs) {
        super(deviceNumber, controlPeriodMs);
    }

    public LazyCANTalon(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public void set(double value) {
        if (value != mLastSet || getControlMode() != mLastControlMode) {
            mLastSet = value;
            mLastControlMode = getControlMode();
            super.set(value);
        }
    }
}
