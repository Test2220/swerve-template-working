package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class AutoRampPowerIntake extends CommandBase {
    private Intake intake;

    public AutoRampPowerIntake(Intake intake){
        this.intake = intake;

        addRequirements(intake);
    }

    public double sensorPosition = -1;
    public double previousSensorPosition;
    public int staticCycles = 0;
    public double intakeDraw;

    public void initialize() {

    } 

    public void execute() {
        intakeDraw = 0;
        sensorPosition = intake.getSensorPosition();
        double change = previousSensorPosition - sensorPosition;
        // System.out.println(sensorPosition);
        if (staticCycles > 50 && staticCycles < 100){
            intake.setPower(Constants.INTAKE_UNJAM_POWER_MAX);
            staticCycles++;
            //System.out.println("Max Power");
        } else if (change < Constants.INTAKE_THRESHOLD && change > -Constants.INTAKE_THRESHOLD) {
            intake.setPower(Constants.INTAKE_UNJAM_POWER);
            staticCycles++;
            //System.out.println("Increasing Power");
        } else if (staticCycles >= 100) {
            intake.setPower(0);
        } else {
            intake.setPower(Constants.INTAKE_POWER);
            staticCycles = 0;
        }
        previousSensorPosition = sensorPosition;
    }

    @Override
    public void end(boolean interupted) {
        intake.setPower(0);
    }
}
