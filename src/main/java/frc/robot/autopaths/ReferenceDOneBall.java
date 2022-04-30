package frc.robot.autopaths;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.FieldConstants;
import frc.robot.commands.GoToCommand;
import frc.robot.commands.RunShooterVelocity;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.GeomUtil;

public class ReferenceDOneBall extends SequentialCommandGroup {
    
    public ReferenceDOneBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceDRobotCenter))),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceD)),

            new RunShooterVelocity(shooter, conveyor, Constants.AUTO_LOW_GOAL).withTimeout(2),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.oneBallTaxiPoseFromReferencPose2d(FieldConstants.referenceDRobotCenter)))

        );
    }
}
