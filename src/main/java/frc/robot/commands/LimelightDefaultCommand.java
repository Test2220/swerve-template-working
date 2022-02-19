package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;

/**
 * Runs while limelight is not being used -- allows to turn LEDs off when not
 * necessary.
 * 
 * @author Dhruv
 */
public class LimelightDefaultCommand extends CommandBase {
    Limelight limelight;

    /**
     * Constructor that requires the limelight, preventing other commands from using
     * the limelight while this is active.
     */
    public LimelightDefaultCommand(Limelight limelight) {
        this.limelight = limelight;
        addRequirements(limelight);

    }

    /**
     * Turn's off LEDs when this command is initialized, or essentially when no
     * other command is active.
     */
    // @Override
    // public void initialize() {
    //     limelight.setCameraMode(CameraMode.DRIVER_CAMERA);
    //     limelight.setLEDMode(LEDMode.ON);
    // }

    /**
     * Called when the command ends because somebody called cancel() or another
     * command shared the same requirements as this one, and booted it out. Turns
     * the limelight LEDs on when no other command is using it.
     */
    @Override
    public void end(boolean interrupted) {
    // if(interrupted){
    //     limelight.setCameraMode(CameraMode.VISION_PROCESSING);
    //     limelight.setLEDMode(LEDMode.USE_PIPELINE);
    // }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}