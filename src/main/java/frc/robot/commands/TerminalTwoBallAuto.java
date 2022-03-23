package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.GeomUtil;

public class TerminalTwoBallAuto extends SequentialCommandGroup {
    
    public TerminalTwoBallAuto(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceA))),

            // new RunShooter(shooter, conveyor, true).withTimeout(2),

            // new ExtendIntake(intake),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceB)),
            // .raceWith(new RunIntake(intake, false))

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceA))

            // new RunShooter(shooter, conveyor, true).withTimeout(4)
        );
    }
}