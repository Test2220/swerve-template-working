package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;

public class Trajectories {
    //public final Trajectory testTrajectory = trajectoryFromPath("output/Barrell.wpilib.json");
   // public final Trajectory testTrajectory = trajectoryFromPath("output/Bounce.wpilib.json");
    public static final Trajectory testTrajectory = trajectoryFromPath("output/Test.wpilib.json");
    //public final Trajectory testTrajectory = trajectoryFromPath("output/GalacticAIntakeTest.wpilib.json");



    public static Trajectory trajectoryFromPath(String trajectoryJSON){
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
