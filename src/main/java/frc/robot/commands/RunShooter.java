package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase {
    private Shooter shooter;
    private Conveyor conveyor;
    private boolean lowGoal;

    public RunShooter(Shooter shooter, Conveyor conveyor, boolean lowGoal) {
        this.shooter = shooter;
        this.conveyor = conveyor;
        this.lowGoal = lowGoal;

        addRequirements(shooter, conveyor);
    }

    public void intialize() {
    }

    public void execute() {
        if (lowGoal) {
            shooter.setPower(Constants.SHUFFLEBOARD_SHOOTER_POWER_LOW.getDouble(Constants.SHOOTER_POWER_LOW));
            // shooter.setPower(0.3);
        }
        else {
            shooter.setPower(Constants.SHUFFLEBOARD_SHOOTER_POWER_HIGH.getDouble(Constants.SHOOTER_POWER_HIGH));
            // shooter.setPower(0.75);
        }
        conveyor.setPower(Constants.SHUFFLEBOARD_CONVEYOR_SPEED.getDouble(Constants.CONVEYOR_POWER));
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setPower(0);
        conveyor.setPower(0);
    }
}