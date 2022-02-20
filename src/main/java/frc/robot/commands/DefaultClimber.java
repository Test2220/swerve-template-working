package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class DefaultClimber extends CommandBase {
    private DoubleSupplier leftClimb;
    private DoubleSupplier rightClimb;

    private final Climber climber;

    public DefaultClimber(Climber c, DoubleSupplier left, DoubleSupplier right) {
        leftClimb = left;
        rightClimb = right;
        climber = c;

        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.setLeft(leftClimb.getAsDouble() * Constants.CLIMBER_POWER);
        climber.setRight(rightClimb.getAsDouble() * Constants.CLIMBER_POWER);
    }
}
