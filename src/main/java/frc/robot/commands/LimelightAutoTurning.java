package frc.robot.commands;

import java.util.function.DoubleConsumer;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.CameraMode;
import frc.robot.subsystems.Limelight.LEDMode;

public class LimelightAutoTurning extends PIDCommand{
    Limelight limelight ;
    int pipeline;
    
    public LimelightAutoTurning(DoubleConsumer useOutput, Limelight limelight, int pipeline, Subsystem... requirements) {

        super(new PIDController(0.01, 0, 0), limelight::getHOffset, () -> 0, useOutput, requirements);
        addRequirements(limelight);
        this.limelight = limelight;
        this.pipeline = pipeline;
        
    }
   @Override
   public void initialize() {
       super.initialize();
       limelight.setPipeline(pipeline);
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