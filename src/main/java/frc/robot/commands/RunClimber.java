package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class RunClimber extends CommandBase {
    private Climber climber;
    private boolean goesUp;

    public RunClimber(Climber climber, boolean goesUp) {
        this.climber = climber;
        this.goesUp = goesUp;
        addRequirements(climber);
    }

    public void intialize() {

    }
    public void execute() {
        if(goesUp == true) {
            climber.setPower(Constants.CLIMBER_POWER);
        } else {
            climber.setPower(-Constants.CLIMBER_POWER);
        }
    }

    @Override
    public void end(boolean interupted) {
        climber.setPower(0);
    }
}
