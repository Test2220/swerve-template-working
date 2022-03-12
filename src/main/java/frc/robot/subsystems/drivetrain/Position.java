package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Position {
    private double x, y, rot;

    public Position(double x, double y, double rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }

    public void addPos(double x, double y, double rot) {
        this.x += x;
        this.y += y;
        this.rot = rot;
    }

    public double[] getPos() {
        double out[] = {x, y, rot};
        return out;
    }

    public Pose2d convertToPose() {
        return new Pose2d(x, y, new Rotation2d(rot));
    }
}