package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class RunClimber extends CommandBase {
    private Climber climber;

    public RunClimber(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    public void intialize() {

    }
    public void execute() {
        climber.setPower(Constants.CLIMBER_POWER);
    }
    public void end() {

    }
}
