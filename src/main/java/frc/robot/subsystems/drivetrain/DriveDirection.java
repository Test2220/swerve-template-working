package frc.robot.subsystems.drivetrain;

import static frc.robot.Constants.*;

public class DriveDirection {
    private double fwd, str, rot, facing;

    /**
     * Creates a DriveDirection object
     * @param fwd Speed on forward (y) axis. 0.0 to 1.0, where 0 is 0% and 1 is 100%.
     * @param str Speed on strafe (x) axis. 0.0 to 1.0, where 0 is 0% and 1 is 100%.
     * @param rot Rotational velocity. 0.0 to 1.0, where 0 is 0% and 1 is 100%.
     * @param facing Direction robot is facing in radians.
     */
    public DriveDirection(double fwd, double str, double rot, double facing) {
        double max = Math.max(Math.max(fwd, str), rot);
        if (max > 1) {
            fwd /= max;
            str /= max;
            rot /= max;
        }
        
        this.fwd = fwd;
        this.str = str;
        this.rot = rot;
        this.facing = facing;
    }


    /**
     * Creates a DriveDirection object, with option to use Meters/second instead of %
     * @param fwd Speed on forward (y) axis. 0.0 to 1.0, where 0 is 0% and 1 is 100%. If metersPerSecond is true, this is a value in meters per second.
     * @param str Speed on strafe (x) axis. 0.0 to 1.0, where 0 is 0% and 1 is 100%. If metersPerSecond is true, this is a value in meters per second.
     * @param rot Rotational velocity. 0.0 to 1.0, where 0 is 0% and 1 is 100%. If metersPerSecond is true, this is a value in radians per second.
     * @param facing Direction robot is facing in radians.
     */
    public DriveDirection(double fwd, double str, double rot, double facing, boolean metersPerSecond) {
        if (metersPerSecond) {
            fwd /= MAX_VELOCITY_METERS_PER_SECOND;
            str /= MAX_VELOCITY_METERS_PER_SECOND;
            rot /= MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
        }

        double max = Math.max(Math.max(fwd, str), rot);
        if (max > 1) {
            fwd /= max;
            str /= max;
            rot /= max;
        }

        this.fwd = fwd;
        this.str = str;
        this.rot = rot;
        this.facing = facing;
    }


    /**
     * Gets the % of max movement in the forward (y) direction
     * @return 0.0 - 1.0
     */
    public double getFwd() {
        return fwd;
    }

    /**
     * Gets the % of max movement in the strafe (x) direction
     * @return 0.0 - 1.0
     */
    public double getStr() {
        return str;
    }

    /**
     * Gets the % of max movement in the rotational direction
     * @return 0.0 - 1.0
     */
    public double getRot() {
        return rot;
    }

    /**
     * Gets the current facing of the robots direction in terms of movement.
     * @return angle in radians
     */
    public double getFacing() {
        return facing;
    }

    public void setStr(double x) {
        str = x;
    }

    public void setFwd(double y) {
        fwd = y;
    }

    /**
     * Zeros the facing of the movement. Useful for field centric driving.
     */
    public void zero() {
        double fwdOut = ((fwd * Math.cos(facing)) + (str * Math.sin(facing)));
        double strOut = ((str * Math.cos(facing)) - (fwd * Math.sin(facing)));
    
        fwd = fwdOut;
        str = strOut;
        facing = 0;
    }
}
