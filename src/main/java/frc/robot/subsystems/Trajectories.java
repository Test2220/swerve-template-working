package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;

public class Trajectories {
  public static final PathPlannerTrajectory testTrajectory = PathPlanner.loadPath("TestPath", 1, 1);

  public static final Trajectory hangarTwoBall = trajectoryFromPath("output/hangarTwoBall.wpilib.json");

  public static final Trajectory hangarTwoBallBackward = trajectoryFromPath("output/hangarTwoBallBackward.wpilib.json");

  public static final Trajectory testCircleTrajectory = makeTrajectory(
      new Pose2d(0, 0, new Rotation2d(0)),
      List.of(new Translation2d(0, -3), new Translation2d(3, -3), new Translation2d(3, 0)),
      new Pose2d(0, 0, new Rotation2d(0)));

  public static Trajectory makeTrajectory(Pose2d start, List<Translation2d> interiorWaypoints, Pose2d end) {
    TrajectoryConfig config = new TrajectoryConfig(
        Drivetrain.MAX_VELOCITY_METERS_PER_SECOND,
        Constants.kMaxAccelerationMetersPerSecondSquared)
            .setKinematics(Drivetrain.m_kinematics);

    return TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, config);
  }

  public static Trajectory trajectoryFromPath(String trajectoryJSON) {
    Trajectory trajectory = new Trajectory();
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }
    return trajectory;

  }


}
