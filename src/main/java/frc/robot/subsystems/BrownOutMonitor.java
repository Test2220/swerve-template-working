package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
 
public class BrownOutMonitor extends SubsystemBase {
    private boolean hasEverBrownedOut = false;
    private double lastBrownOutTime = -1;
    private final XboxController manipulatorController;

public BrownOutMonitor(XboxController manipulatorController){
    this.manipulatorController = manipulatorController;
}
    @Override
    public void periodic() {
        if (RobotController.isBrownedOut()) {
            hasEverBrownedOut = true;
            lastBrownOutTime = Timer.getFPGATimestamp();
        } 
        if(hasEverBrownedOut() && getSecondsSinceLastBrownOut() < 5){
            manipulatorController.setRumble(GenericHID.RumbleType.kLeftRumble, 0.5);
            manipulatorController.setRumble(GenericHID.RumbleType.kRightRumble, 0.5);
          }
        else{
            manipulatorController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
            manipulatorController.setRumble(GenericHID.RumbleType.kRightRumble, 0);
          }
    } 

    public boolean hasEverBrownedOut() {
        return hasEverBrownedOut;
    }
    public double getSecondsSinceLastBrownOut() {
        if(hasEverBrownedOut) {
        
        return Timer.getFPGATimestamp() - lastBrownOutTime;
        }
        return(-1);
    }
}