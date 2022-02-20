package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.Shooter;

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
        shooter.setPower(Constants.SHUFFLEBOARD_SHOOTER_POWER.getDouble(Constants.SHOOTER_POWER));
        conveyor.setPower(Constants.CONVEYOR_POWER);
        
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setPower(0);
        conveyor.setPower(0);
    }
}