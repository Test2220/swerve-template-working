package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase {
    private Shooter shooter;
    private Conveyor conveyor;
    public RunShooter(Shooter shooter, Conveyor conveyor) {
        this.shooter = shooter;
        this.conveyor = conveyor;
        addRequirements(shooter, conveyor);
    }

    public void intialize() {
    }

    public void execute() {
        shooter.setPower(Constants.SHOOTER_POWER);
        conveyor.setPower(Constants.CONVEYOR_POWER);
        
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setPower(0);
        conveyor.setPower(0);
    }
}