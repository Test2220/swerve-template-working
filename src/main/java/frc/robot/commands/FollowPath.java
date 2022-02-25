package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class FollowPath extends CommandBase {
    Pose2d initialPose2d;
    Drivetrain drivetrainSubsystem;
    SwerveControllerCommand swerveControllerCommand;

    public FollowPath(Trajectory trajectory, Drivetrain drivetrainSubsystem) { 
    Transform2d transform = new Transform2d(trajectory.getInitialPose(), new Pose2d());
    trajectory = trajectory.transformBy(transform);
       swerveControllerCommand = new SwerveControllerCommand(
            trajectory, 
            drivetrainSubsystem::getPose, 
            Drivetrain.m_kinematics, 
            new PIDController(Constants.kPXController, 0, 0), 
            new PIDController(Constants.kPYController, 0, 0),
            FollowPath.getThetaController(),
            drivetrainSubsystem::setModuleStates,
            drivetrainSubsystem);
            initialPose2d = trajectory.getInitialPose();
            this.drivetrainSubsystem = drivetrainSubsystem;
    }

    private static ProfiledPIDController getThetaController() {
        var thetaController = new ProfiledPIDController(
                Constants.kPThetaController, 0, 0, Constants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        return thetaController;
    }

    @Override
    public void initialize() {
        // Reset odometry to the starting pose of the trajectory.
        drivetrainSubsystem.resetOdometry(initialPose2d);
        swerveControllerCommand.initialize();
    }
    @Override
    public void execute() {
        swerveControllerCommand.execute();
    }

    @Override
    public void end(boolean interupted) {
        // Run path following command, then stop at the end.
        drivetrainSubsystem.drive(0, 0, 0, false);
        swerveControllerCommand.end(interupted);
    }

    @Override
    public boolean isFinished() {
        return swerveControllerCommand.isFinished();
    }
}
