package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Shooter;

public class RunShooter extends CommandBase {
    private Shooter shooter;
    public RunShooter(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    public void intialize() {
    }

    public void execute() {
        shooter.setPower(Constants.SHOOTER_POWER);
        
    }
    public void end() {
        shooter.setPower(0);
    }
}