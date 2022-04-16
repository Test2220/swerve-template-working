package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class RunShooterVelocity extends CommandBase {
    private Shooter shooter;
    private Conveyor conveyor;
    private boolean launchGoal;

    public RunShooterVelocity(Shooter shooter, Conveyor conveyor, boolean launchGoal) {
        this.shooter = shooter;
        this.conveyor = conveyor;
        this.launchGoal = launchGoal;
    }

    public void initalize() {
        shooter.setVoltageComp(true);
    }

    public void execute() {
        if (launchGoal) {
            shooter.setLaunchpadVelocity();
            if (shooter.withinLaunchTolerance())
                conveyor.setSpeed(Constants.CONVEYOR_VELOCITY_LAUNCH.getValue() * 2048.0 / 600.0);
            else
                conveyor.setPower(0);
        }
        else {
            shooter.setHighVelocity();
            if (shooter.withinHighTolerance())
                conveyor.setSpeed(Constants.CONVEYOR_VELOCITY_HIGH.getValue() * 2048.0 / 600.0);
            else
                conveyor.setPower(0);
        }
    }

    public void end(boolean interrupted) {
        shooter.setPower(0);
        conveyor.setPower(0);
    }
}