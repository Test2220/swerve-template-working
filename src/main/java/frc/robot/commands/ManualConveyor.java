package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class ManualConveyor extends CommandBase {
    ConveyorSubsystem conveyorSubsystem;
    double power;

    public ManualConveyor(ConveyorSubsystem conveyorSubsystem, double power) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.power = power;

        addRequirements(conveyorSubsystem);
    }


    public void execute() {
        conveyorSubsystem.setPower(power);

    }

}
