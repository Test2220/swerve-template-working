package frc.robot.autopaths;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.GoToCommand;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooterVelocity;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.GeomUtil;

public class ReferenceATwoBall extends SequentialCommandGroup {
    
    public ReferenceATwoBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceARobotCenter))),
            
            new ExtendIntake(intake),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.referenceARobotCenter.getTranslation(), 
                        FieldConstants.cargoB.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(-47.5) //Make more negative when ball hits right side of intake
                        ))
            )).raceWith(new RunIntake(intake, false)),
            
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceA).transformBy(new Transform2d(new Translation2d(), Rotation2d.fromDegrees(-6.25)))).raceWith(new RunIntake(intake, false)),

            new RetractIntake(intake),

            new RunShooterVelocity(shooter, conveyor, false).raceWith(new RunIntake(intake, false)).withTimeout(5)
        );
    }
}