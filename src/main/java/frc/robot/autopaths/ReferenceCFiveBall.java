package frc.robot.autopaths;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
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

public class ReferenceCFiveBall extends SequentialCommandGroup {
    
    public ReferenceCFiveBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceCRobotCenter))),
           
            new RunShooter(shooter, conveyor, Constants.AUTO_LOW_GOAL).withTimeout(1),

            new ExtendIntake(intake),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.referenceCRobotCenter.getTranslation(), 
                        FieldConstants.cargoD.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(-10)
                        ))
            )).raceWith(new RunIntake(intake, false)),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.cargoD.getTranslation(), 
                        FieldConstants.cargoE.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(15)
                        ))
            )).raceWith(new RunIntake(intake, false)),
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceCRobotCenter)).raceWith(new RunIntake(intake, false)),

            new RetractIntake(intake),

            new RunShooter(shooter, conveyor, Constants.AUTO_LOW_GOAL).raceWith(new RunIntake(intake, false)).withTimeout(3),

            new ExtendIntake(intake),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.referenceCRobotCenter.getTranslation(), 
                        FieldConstants.cargoG.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(-20)
                        ))
            )).raceWith(new RunIntake(intake, false)),    
                   
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceCRobotCenter)).raceWith(new RunIntake(intake, false)),

            new RetractIntake(intake),

            new RunShooter(shooter, conveyor, Constants.AUTO_LOW_GOAL).raceWith(new RunIntake(intake, false)).withTimeout(3)
            );
        
   }
}