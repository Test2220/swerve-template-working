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
        // TODO add overide

        climber.setLeft(
            climber.clamp(
                climber.getLeftLimitTop(), 
                climber.getLeftLimitBottom(), 
                leftClimb.getAsDouble() * Constants.CLIMBER_POWER,
                false
            )
        );

        climber.setRight(
            climber.clamp(
                climber.getRightLimitTop(), 
                climber.getRightLimitBottom(), 
                rightClimb.getAsDouble() * Constants.CLIMBER_POWER,
                false
            )
        );
    }
}
