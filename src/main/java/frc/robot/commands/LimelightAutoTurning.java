package frc.robot.commands;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.CameraMode;
import frc.robot.subsystems.Limelight.LEDMode;

public class LimelightAutoTurning extends PIDCommand{
    Limelight limelight ;
    
    public LimelightAutoTurning(PIDController controller, DoubleSupplier measurementSource,
            DoubleSupplier setpointSource, DoubleConsumer useOutput, Limelight limelight,
             Subsystem... requirements) {

        super(controller, measurementSource, setpointSource, useOutput, requirements);
        addRequirements(limelight);
        this.limelight = limelight;
        
    }
   @Override
   public void initialize() {
       super.initialize();
       limelight.setCameraMode(CameraMode.VISION_PROCESSING);
       limelight.setLEDMode(LEDMode.ON);
    } 
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        limelight.setCameraMode(CameraMode.DRIVER_CAMERA);
        limelight.setLEDMode(LEDMode.OFF);
    }
}