package frc.robot.subsystems.drivetrain;

// import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

// import static frc.robot.Constants.*;

import java.util.function.DoubleSupplier;

public class VelocityControl {
    // private TrapezoidProfile xProfile;
    // private TrapezoidProfile yProfile;

    // private TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(20, 3);
    
    // private TrapezoidProfile.State xEndState;
    // private TrapezoidProfile.State xCurrentState;
    private double xEnd;
    private double xStart;
    
    // private TrapezoidProfile.State yEndState;
    // private TrapezoidProfile.State yCurrentState;
    private double yEnd;
    private double yStart;

    private double rotEnd;
    private double rotStart;

    private double time;

    private DoubleSupplier xSupplier = () -> (xStart + (xEnd - xStart) / Math.pow(1 + Math.exp(-(16 / (Math.abs(xEnd - xStart))) * time), 8));
    private DoubleSupplier ySupplier = () -> (yStart + (yEnd - yStart) / Math.pow(1 + Math.exp(-(16 / (Math.abs(yEnd - yStart))) * time), 8));
    private DoubleSupplier rotSupplier = () -> (rotStart + (rotEnd - rotStart) / Math.pow(1 + Math.exp(-(16 / (Math.abs(rotEnd - rotStart))) * time), 8));

    private double timeSinceLastCheck;
    
    public VelocityControl(Position endPosition, Position current) {
        double in[] = endPosition.getPos();

        double in2[] = current.getPos();

        xEnd = in[0];
        xStart = in2[0];

        yEnd = in[1];
        yStart = in2[1];

        rotEnd = wrap(in[2]);
        rotStart = wrap(in2[2]);
        
        // xEndState = new TrapezoidProfile.State(in[0], 0);
        // yEndState = new TrapezoidProfile.State(in[1], 0);

        // xCurrentState = new TrapezoidProfile.State(in2[0], currentVel.getStr() * MAX_VELOCITY_METERS_PER_SECOND);
        // yCurrentState = new TrapezoidProfile.State(in2[1], currentVel.getFwd() * MAX_VELOCITY_METERS_PER_SECOND);

        timeSinceLastCheck = Timer.getFPGATimestamp();
    }

    public Position getNextPos() {
        time = (Timer.getFPGATimestamp() - timeSinceLastCheck);

        double x = xSupplier.getAsDouble();
        double y = ySupplier.getAsDouble();
        double rot = rotSupplier.getAsDouble();

        // xCurrentState = new TrapezoidProfile.State(in[0], currentVel.getStr() * MAX_VELOCITY_METERS_PER_SECOND);
        // yCurrentState = new TrapezoidProfile.State(in[1], currentVel.getFwd() * MAX_VELOCITY_METERS_PER_SECOND);

        // xProfile = new TrapezoidProfile(constraints, xEndState, xCurrentState);
        // yProfile = new TrapezoidProfile(constraints, yEndState, yCurrentState);

        // double x = xProfile.calculate(time).position;
        // double y = yProfile.calculate(time).position;

        ShuffleboardPID.xPos.setDouble(x);
        ShuffleboardPID.yPos.setDouble(y);
        ShuffleboardPID.rPos.setDouble(rot);

        return new Position(x, y, rot);
    }

    public boolean isDone() {
        return (Math.abs(xSupplier.getAsDouble() - xEnd) < 0.02) && 
            (Math.abs(ySupplier.getAsDouble() - yEnd) < 0.02) && 
            (Math.abs(rotSupplier.getAsDouble() - rotEnd) < 0.02);
    }

    private double wrap(double in) {
        double out = in % (Math.PI * 2);
        if (out > Math.PI) out -= Math.PI * 2;
        return out;
    }
}
