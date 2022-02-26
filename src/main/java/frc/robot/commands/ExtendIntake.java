package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.Position;

public class ExtendIntake extends CommandBase {
    private final Intake intake;
    

    public ExtendIntake(Intake intake) {
        this.intake = intake; 
        addRequirements(intake);
    }

    public void initialize() {
        intake.setPosition(Position.EXTENDED);
    }
    public void execute() {
        // intake.setPower(Constants.INTAKE_POWER);
    }
    public void end(boolean interrupted) {
        // intake.setPosition(Position.RETRACTED);
    }
    public boolean isFinished() {
        return true;
    }
}