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

public class ReferenceDFiveBall extends SequentialCommandGroup {
    
    public ReferenceDFiveBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceDRobotCenter))),
           
            new RunShooter(shooter, conveyor, Constants.AUTO_LOW_GOAL).withTimeout(1),

            new ExtendIntake(intake),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.referenceDRobotCenter.getTranslation(), 
                        FieldConstants.cargoE.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(10)
                        ))
            )).raceWith(new RunIntake(intake, false)),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.cargoE.getTranslation(), 
                        FieldConstants.cargoD.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(-15)
                        ))
            )).raceWith(new RunIntake(intake, false)),
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceDRobotCenter)).withSpeed(0.5).raceWith(new RunIntake(intake, false)),

            new RetractIntake(intake),

            new RunShooter(shooter, conveyor, Constants.AUTO_LOW_GOAL).raceWith(new RunIntake(intake, false)).withTimeout(3),

            new ExtendIntake(intake),

            new GoToCommand(
                drivetrain, 
                GeomUtil.getRobotCoordinate(
                    GeomUtil.poseToGetCargo(
                        FieldConstants.referenceDRobotCenter.getTranslation(), 
                        FieldConstants.cargoG.getTranslation()
                    ).transformBy(
                        new Transform2d(
                            new Translation2d(), 
                            Rotation2d.fromDegrees(-20)
                        ))
            )).withSpeed(0.5).raceWith(new RunIntake(intake, false)),    
                   
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceDRobotCenter)).raceWith(new RunIntake(intake, false)),

            new RetractIntake(intake),

            new RunShooter(shooter, conveyor, Constants.AUTO_LOW_GOAL).raceWith(new RunIntake(intake, false)).withTimeout(3)
            );
        
   }
}