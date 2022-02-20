package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Shooter;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunShooter extends CommandBase {
    private Shooter shooter;
    private ConveyorSubsystem conveyor;
    public RunShooter(Shooter shooter, ConveyorSubsystem conveyor) {
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
    public void end() {
        shooter.setPower(0);
    }
}