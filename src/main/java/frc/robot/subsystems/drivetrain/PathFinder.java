package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

public class PathFinder {
    private double[] startPos = new double[2];

    private PIDController xController;
    private PIDController yController;
    private PIDController rController;
    
    private PathPlannerTrajectory control;

    private double timeSinceLastCheck;

    public PathFinder(String path, Position currentPos) {
        xController = new PIDController(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        yController = new PIDController(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        rController = new PIDController(ShuffleboardPID.rotP.getDouble(0), ShuffleboardPID.rotI.getDouble(0), ShuffleboardPID.rotD.getDouble(0));

        rController.enableContinuousInput(-Math.PI, Math.PI);
        rController.setTolerance(0.2);

        control = PathPlanner.loadPath(path, 8, 5);

        double[] start = currentPos.getPos();
        Pose2d state = control.sample(0).poseMeters;

        startPos[0] = start[0] + state.getX();
        startPos[1] = start[1] + state.getY();

        timeSinceLastCheck = Timer.getFPGATimestamp();
    }
    
    public DriveDirection getDirection(Position currentPos) {
        double current[] = currentPos.getPos();

        xController.setPID(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        yController.setPID(ShuffleboardPID.axisP.getDouble(0), ShuffleboardPID.axisI.getDouble(0), ShuffleboardPID.axisD.getDouble(0));
        rController.setPID(ShuffleboardPID.rotP.getDouble(0), ShuffleboardPID.rotI.getDouble(0), ShuffleboardPID.rotD.getDouble(0));

        double time = Timer.getFPGATimestamp() - timeSinceLastCheck;

        PathPlannerState state = (PathPlannerState) control.sample(time);

        double next[] = {state.poseMeters.getX() - startPos[0], state.poseMeters.getY() - startPos[1], state.holonomicRotation.getRadians()};

        double xOut = xController.calculate(current[0], next[0]);
        double yOut = yController.calculate(current[1], next[1]);
        double rOut = rController.calculate(current[2], -(next[2] - (Math.PI / 2)) % (2 * Math.PI));

        return new DriveDirection(yOut, xOut, rOut, current[2]);
    }

    public boolean isDone() {
        double time = Timer.getFPGATimestamp() - timeSinceLastCheck;

        boolean done = 
            xController.atSetpoint() 
            && yController.atSetpoint() 
            && rController.atSetpoint()
            && time > control.getTotalTimeSeconds();

        return done;
    }
}