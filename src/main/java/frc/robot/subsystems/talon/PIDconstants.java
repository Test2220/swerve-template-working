package frc.robot.subsystems.talon;

public class PIDconstants {
    /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
     */
    public static final int kSlotIdx = 0;

    /**
	 * Talon FX supports multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
    public static final int kPIDLoopIdx = 0;

    /**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
    public static final int kTimeoutMs = 30;

    public static final double kP = 0.1;
    public static final double kI = 0.001;
    public static final double kD = 5;
    public static final double kF = 0;

    public static final int kIzone = 300;
    public static final double kPeakOutput = 1;
}
