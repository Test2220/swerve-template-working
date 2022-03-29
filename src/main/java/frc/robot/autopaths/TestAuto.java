package frc.robot.autopaths;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.commands.GoToCommand;
import frc.robot.commands.RunIntake;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.drivetrain.Position;
import frc.robot.util.GeomUtil;

public class TestAuto extends SequentialCommandGroup {
    public TestAuto(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            // new InstantCommand(()->drivetrain.setPose(new Position(0, 0, 0).convertToPose())),
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceBRobotCenter))),
            
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(GeomUtil.poseToGetCargo(FieldConstants.referenceBRobotCenter.getTranslation(), FieldConstants.cargoB.getTranslation()))).raceWith(new RunIntake(intake, false)),
            
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceBRobotCenter)).raceWith(new RunIntake(intake, false))


            // new GoToCommand(drivetrain, new Position(0, 0, 0)),
            // new GoToCommand(drivetrain, new Position(2, 2, Math.PI)),
            // new GoToCommand(drivetrain, new Position(0, 0, 0)),
            // new GoToCommand(drivetrain, new Position(2, 2, 1.5 * Math.PI)),
            // new GoToCommand(drivetrain, new Position(0, 0, 0)),
            // new GoToCommand(drivetrain, new Position(2, 2, 0.5 * Math.PI))
        );
    }
}
