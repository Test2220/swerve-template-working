package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class DefaultClimber extends CommandBase {
    private DoubleSupplier leftClimb;
    private DoubleSupplier rightClimb;

    private final Climber climber;

    public BooleanSupplier leftOverridBooleanSupplier;
    public BooleanSupplier rightOverridBooleanSupplier;

    public DefaultClimber(Climber c, DoubleSupplier left, DoubleSupplier right, BooleanSupplier LOverride, BooleanSupplier ROverride) {
        leftClimb = left;
        rightClimb = right;
        climber = c;
        leftOverridBooleanSupplier = LOverride;
        rightOverridBooleanSupplier = ROverride;

        addRequirements(climber);
    }



    @Override
    public void execute() {

        climber.setLeft(
            climber.clamp(
                climber.getLeftLimitTop(), 
                climber.getLeftLimitBottom(), 
                leftClimb.getAsDouble() * Constants.CLIMBER_POWER,
                leftOverridBooleanSupplier.getAsBoolean()
            )
        );

        climber.setRight(
            climber.clamp(
                climber.getRightLimitTop(), 
                climber.getRightLimitBottom(), 
                rightClimb.getAsDouble() * Constants.CLIMBER_POWER,
                rightOverridBooleanSupplier.getAsBoolean()
            )
        );
    }
}
