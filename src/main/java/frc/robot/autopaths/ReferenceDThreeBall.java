package frc.robot.autopaths;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.GoToCommand;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.GeomUtil;

public class ReferenceDThreeBall extends SequentialCommandGroup {
    
    public ReferenceDThreeBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceDRobotCenter))),

            new RunShooter(shooter, conveyor, true).withTimeout(2),

            new ExtendIntake(intake),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.cargoD)).raceWith(new RunIntake(intake, false)),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.cargoE)).raceWith(new RunIntake(intake, false)),
            
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceDRobotCenter)).raceWith(new RunIntake(intake, false)),

            new RunShooter(shooter, conveyor, true).raceWith(new RunIntake(intake, false)).withTimeout(5)
        );
    }
}
