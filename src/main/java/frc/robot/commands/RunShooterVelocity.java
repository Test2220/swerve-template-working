package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class RunShooterVelocity extends CommandBase {
    private Shooter shooter;
    private Conveyor conveyor;
    private boolean lowGoal;

    public RunShooterVelocity(Shooter shooter, Conveyor conveyor, boolean lowGoal) {
        this.shooter = shooter;
        this.conveyor = conveyor;
        this.lowGoal = lowGoal;
    }

    public void execute() {
        if (lowGoal) {
            shooter.setLowVelocity();
        }
        else {
            shooter.setHighVelocity();
        }
        conveyor.setPower(Constants.CONVEYOR_POWER);
    }

    public void end(boolean interrupted) {
        shooter.setPower(0);
        conveyor.setPower(0);
    }
}