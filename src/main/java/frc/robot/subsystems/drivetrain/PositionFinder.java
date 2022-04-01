package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.util.Units;

public class PositionFinder {
    private PIDController xController;
    private PIDController yController;
    private PIDController rController;

    private VelocityControl vControl;

    public PositionFinder(Position in, Position currentPos) {
        xController = new PIDController(ShuffleboardPID.axisP.getAsDouble(), ShuffleboardPID.axisI.getAsDouble(), ShuffleboardPID.axisD.getAsDouble());
        yController = new PIDController(ShuffleboardPID.axisP.getAsDouble(), ShuffleboardPID.axisI.getAsDouble(), ShuffleboardPID.axisD.getAsDouble());
        rController = new PIDController(ShuffleboardPID.rotP.getAsDouble(), ShuffleboardPID.rotI.getAsDouble(), ShuffleboardPID.rotD.getAsDouble());

        rController.enableContinuousInput(-Math.PI, Math.PI);
        rController.setTolerance(0.2);

        vControl = new VelocityControl(in, currentPos);
        // System.out.println("Current Position:" + Units.radiansToDegrees(in.getPos()[2]));

       }
    
    public DriveDirection getDirection(Position currentPos) {
        double current[] = currentPos.getPos();

        xController.setPID(ShuffleboardPID.axisP.getAsDouble(), ShuffleboardPID.axisI.getAsDouble(), ShuffleboardPID.axisD.getAsDouble());
        yController.setPID(ShuffleboardPID.axisP.getAsDouble(), ShuffleboardPID.axisI.getAsDouble(), ShuffleboardPID.axisD.getAsDouble());
        rController.setPID(ShuffleboardPID.rotP.getAsDouble(), ShuffleboardPID.rotI.getAsDouble(), ShuffleboardPID.rotD.getAsDouble());

        Position goTo = vControl.getNextPos();

        double next[] = goTo.getPos();

        double xOut = xController.calculate(current[0], next[0]);
        double yOut = yController.calculate(current[1], next[1]);
        double rOut = rController.calculate(current[2], next[2]);
        // System.out.println("R:" + Units.radiansToDegrees(next[2]));

        return new DriveDirection(yOut, xOut, rOut, current[2]);
    }

    public boolean isDone() {
        boolean done = 
            xController.atSetpoint() &&
            yController.atSetpoint() &&
            rController.atSetpoint() &&
            vControl.isDone();


        return done;
    }
}
