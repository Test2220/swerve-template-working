package frc.robot.commands;

import java.util.function.DoubleConsumer;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.PixyCamSPI;

public class PixyCamAutoTurning extends PIDCommand {
    PixyCamSPI pixyCam;
    byte currentSignature;  
    public PixyCamAutoTurning(DoubleConsumer useOutput, PixyCamSPI pixyCam, byte currentSignature, Subsystem... requirements) {

        super(new PIDController(0.01, 0, 0), pixyCam::getLargestTargetAngle, () -> 0, useOutput, requirements);
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
