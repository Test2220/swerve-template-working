package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.Position;

public class RetractIntake extends CommandBase {
    private final Intake intake;
    

    public RetractIntake(Intake intake) {
        this.intake = intake; 
        addRequirements();
    }

    public void initialize() {
        intake.setPosition(Position.RETRACTED);
    }
    public void execute() {
       intake.setPower(Constants.intakePower);
    }
    public void end(boolean interrupted) {
       intake.setPosition(Position.RETRACTED);
    }
}
