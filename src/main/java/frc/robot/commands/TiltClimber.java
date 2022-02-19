package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.ClimberPositions;

public class TiltClimber extends CommandBase {
    private Climber climber;
    private ClimberPositions position;
        
    public TiltClimber(Climber climber, ClimberPositions position) {
        this.climber = climber;
        this.position = position;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.setPosition(position);
    }
}
