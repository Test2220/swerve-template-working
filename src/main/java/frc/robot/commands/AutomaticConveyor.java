package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;

public class AutomaticConveyor extends CommandBase {
    Conveyor conveyorSubsystem;
    DoubleSupplier manualPower;
    BooleanSupplier manualOveride;
    BooleanSupplier switchToIdle;

    public AutomaticConveyor(Conveyor conveyorSubsystem, DoubleSupplier manualPower, BooleanSupplier manualOveride,
            BooleanSupplier switchToIdle) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.manualPower = manualPower;
        this.manualOveride = manualOveride;
        this.switchToIdle = switchToIdle;
        addRequirements(conveyorSubsystem);

    }

    public void execute() {
        if (manualPower.getAsDouble() != 0) 
        {
            conveyorSubsystem.setPower(manualPower.getAsDouble());
        }
        else
        {
            if (conveyorSubsystem.isBallPresentAtInput()) 
            {
                conveyorSubsystem.setPower(Constants.CONVEYOR_SPEED.getValue());
            } 
            else 
            {
                conveyorSubsystem.setPower(0);
            }
        }

    }

    public void end(boolean interrupted)
    {
        conveyorSubsystem.setPower(0);
    }
}