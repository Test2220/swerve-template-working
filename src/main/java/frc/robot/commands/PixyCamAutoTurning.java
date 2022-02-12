package frc.robot.commands;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.PixyCamSPI;
import frc.robot.subsystems.Limelight.CameraMode;
import io.github.pseudoresonance.pixy2api.Pixy2;

public class PixyCamAutoTurning extends PIDCommand {
    PixyCamSPI pixyCam;
    byte currentSignature;  
    public PixyCamAutoTurning(PIDController controller, DoubleSupplier measurementSource,
            DoubleSupplier setpointSource, DoubleConsumer useOutput, PixyCamSPI pixyCam, byte currentSignature,
             Subsystem... requirements) {

        super(controller, measurementSource, setpointSource, useOutput, requirements);
        addRequirements(pixyCam);
        this.pixyCam = pixyCam;
        this.currentSignature = currentSignature;
    }
    @Override
   public void initialize() {
       super.initialize();
       pixyCam.setCurrentSignature(currentSignature);

    } 
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
