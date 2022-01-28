package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Limelight vision processing and high FOV driver camera subsystem. Contains
 * methods necessary to accessing data from and controlling Limelight camera.
 * This class is to be used in conjunction with other subsystems and/or commands
 * in order achieve automation.
 * 
 * @author Reece
 */
public class Limelight extends SubsystemBase {

    /* INSTANCE VARIABLES */

    // Network table for limelight, has camera data
    private final NetworkTable limelight = NetworkTableInstance.getDefault().getTable(Constants.LIMELIGHT_TABLE_NAME);

    /* SUBSYSTEM CONSTRUCTOR */

    /**
     * Limelight subsystem constructor. No arguments necessary or present.
     */
    public Limelight() {
        // Set camera mode to use vision processing
        setCameraMode(CameraMode.VISION_PROCESSING);

        // Set LED mode to use pipeline
        setLEDMode(LEDMode.USE_PIPELINE);

        // Set streaming mode to standard
        setStreamMode(StreamMode.STANDARD);

        // Disable limelight snapshotting. 0 = off, 1 = on
        limelight.getEntry("snapshot").setNumber(0);
    }

    /* LIMELIGHT DATA GETTERS */

    /**
     * Checks if the Limelight can currently see the calibrated target.
     * 
     * @return Returns a boolean value for if the Limelight can see target (true) or
     *         not (false).
     */
    public boolean seeTarget() {
        NetworkTableEntry targetViewable = limelight.getEntry("tv");
        double val = targetViewable.getDouble(0);
        boolean seeTarget = (val == 1);
        return seeTarget;
    }

    /**
     * Horizontal offset from target visible from Limelight.
     * 
     * @return Returns raw double value from Limelight with range of {-27, 27}
     *         degrees
     */
    public double getHOffset() {
        NetworkTableEntry targetX = limelight.getEntry("tx");
        double val = targetX.getDouble(0);
        return val;
    }

    /**
     * Vertical offset from target visible from Limelight.
     * 
     * @return Returns raw double value from Limelight with range of {-20.5, 20.5}
     *         degrees
     */
    public double getVOffset() {
        NetworkTableEntry targetY = limelight.getEntry("ty");
        double val = targetY.getDouble(0);
        return val;
    }

    /**
     * Size of target in % of screen size visible from Limelight.
     * 
     * @return Returns double value from {0, 100} representing percent of screen
     *         space target takes up
     */
    public double getTargetSize() {
        NetworkTableEntry targetArea = limelight.getEntry("ta");
        double val = targetArea.getDouble(0);
        return val;
    }

    /**
     * Skew of target, or its rotation visible from Limelight.
     * 
     * @return Returns the skew of visible target with range of {-90, 0} degrees
     */
    public double getTargetSkew() {
        NetworkTableEntry targetSkew = limelight.getEntry("ts");
        double val = targetSkew.getDouble(0);
        return val;
    }

    /* LIMELIGHT MEMBER SETTERS */

    /**
     * Set the mode of the camera on the Limelight. Options specified in CameraMode
     * enum.
     * 
     * @param mode The desired camera mode on Limelight
     */
    public void setCameraMode(CameraMode mode) {
        int modeVal = mode.val;
        limelight.getEntry("camMode").setNumber(modeVal);
    }

    /**
     * Set the LED mode of the Limelight. Options specified in LEDMode enum.
     * 
     * @param mode The desired mode of LEDs on Limelight
     */
    public void setLEDMode(LEDMode mode) {
        int modeVal = mode.val;
        limelight.getEntry("ledMode").setNumber(modeVal);
    }

    /**
     * Set the streaming mode of the camera. Available options listed in StreamMode
     * enum.
     * 
     * @param mode The desired streaming mode of the camera
     */
    public void setStreamMode(StreamMode mode) {
        int modeVal = mode.val;
        limelight.getEntry("stream").setNumber(modeVal);
    }

    /**
     * Set which image manipulation pipeline the limelight is currently running.
     * 
     * @param pipeline Set ID of Limelight vision pipeline
     */
    public void setPipeline(int pipeline) {
        limelight.getEntry("pipeline").setNumber(pipeline);
    }

    public void takeSnapshot() {
        limelight.getEntry("snapshot").setNumber(1);
    }

    /* LIMELIGHT DATA STATES ENUMERATION */

    /**
     * Camera modes enumeration
     */
    public enum CameraMode {
        VISION_PROCESSING(0), DRIVER_CAMERA(1);

        private final int val;

        CameraMode(int val) {
            this.val = val;
        }
    }

    /**
     * Led modes enumeration
     */
    public enum LEDMode {
        USE_PIPELINE(0), OFF(1), BLINK(2), ON(3);

        private final int val;

        LEDMode(int val) {
            this.val = val;
        }
    }

    /**
     * Stream modes enumeration, PiP stands for picture in picture
     */
    public enum StreamMode {
        STANDARD(0), PIP_MAIN(1), PIP_SECONDARY(2);

        private final int val;

        StreamMode(int val) {
            this.val = val;
        }
    }

    
}