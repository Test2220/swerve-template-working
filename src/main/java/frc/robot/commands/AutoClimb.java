package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class AutoClimb extends CommandBase {
    private Climber climber;
    private DoubleSupplier tilt;
    private boolean up = true;

    public AutoClimb(Climber climber, DoubleSupplier tilt) {
        this.climber = climber;
        this.tilt = tilt;
        addRequirements(climber);
    }

    public void intialize() {

    }
    public void execute() {
        if (up) {

        }
    }

    public void moveUp() {
        climber.setLeft(0.8);
        climber.setRight(0.8);
    }

    @Override
    public void end(boolean interupted) {
        climber.setPower(0);
    }

    public boolean atBottom() {
        return ((climber.getLeftLimitBottom() || climber.getLeftCurrent() > 60)
             && (climber.getRightLimitBottom() || climber.getRightCurrent() > 60));
    }

    public enum State {
        initalClimb,
        
    }
}
