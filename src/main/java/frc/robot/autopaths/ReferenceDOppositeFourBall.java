package frc.robot.autopaths;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.GoToCommand;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.GeomUtil;

public class ReferenceDOppositeFourBall extends SequentialCommandGroup {
    
    public ReferenceDOppositeFourBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceDOpposite))),

            new ExtendIntake(intake),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.cargoEOpposite)).raceWith(new RunIntake(intake, false)),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceDOpposite)),

            new RunShooter(shooter, conveyor, true).withTimeout(4),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.cargoDOpposite)).raceWith(new RunIntake(intake, false)),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.cargoGOpposite)).raceWith(new RunIntake(intake, false)),
            
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceDOpposite)),

            new RunShooter(shooter, conveyor, true).withTimeout(5)
        );
    }
}