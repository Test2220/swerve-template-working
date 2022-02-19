package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
    private Intake intake;
    public RunIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }

    public void intialize() {

    }
    public void execute() {
        intake.setPower(Constants.INTAKE_POWER);
    }
    public void end() {
        intake.setPower(0);
    }
}
