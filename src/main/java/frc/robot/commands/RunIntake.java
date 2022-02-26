package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
    private Intake intake;
    private boolean reverse;
    public RunIntake(Intake intake, boolean reverse){
        this.intake = intake;
        this.reverse = reverse;

        addRequirements(intake);
    }

    public void intialize() {

    }
    public void execute() {
        if (reverse)
            intake.setPower(-Constants.INTAKE_POWER);
        else
            intake.setPower(Constants.INTAKE_POWER);
    }

    @Override
    public void end(boolean interupted) {
        intake.setPower(0);
    }
}
