package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ManualConveyor extends CommandBase {
    Conveyor conveyorSubsystem;
    double power;

    public ManualConveyor(Conveyor conveyorSubsystem, double power) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.power = power;

        addRequirements(conveyorSubsystem);
    }


    public void execute() {
        conveyorSubsystem.setPower(power);

    }

}
