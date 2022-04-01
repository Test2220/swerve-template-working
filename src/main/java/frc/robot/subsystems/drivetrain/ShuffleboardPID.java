package frc.robot.subsystems.drivetrain;

import java.util.function.DoubleSupplier;


public class ShuffleboardPID {
    // TODO FIX THIS FILE RYAN


    // public static ShuffleboardTab tab = Shuffleboard.getTab("PID");

    // public static NetworkTableEntry axisP = tab.addPersistent("axisP", 0.25)
    //     .withSize(1, 1)
    //     .withPosition(0, 0)
    //     .getEntry();
    // public static NetworkTableEntry axisI = tab.addPersistent("axisI", 0)
    //     .withSize(1, 1)
    //     .withPosition(0, 1)
    //     .getEntry();
    // public static NetworkTableEntry axisD = tab.addPersistent("axisD", 0)
    //     .withSize(1, 1)
    //     .withPosition(0, 2)
    //     .getEntry();

    // public static NetworkTableEntry rotP = tab.addPersistent("rotP", 0.25)
    //     .withSize(1, 1)
    //     .withPosition(1, 0)
    //     .getEntry();
    // public static NetworkTableEntry rotI = tab.addPersistent("rotI", 0)
    //     .withSize(1, 1)
    //     .withPosition(1, 1)
    //     .getEntry();
    // public static NetworkTableEntry rotD = tab.addPersistent("rotD", 0)
    //     .withSize(1, 1)
    //     .withPosition(1, 2)
    //     .getEntry();


    // public static NetworkTableEntry xPos = tab.addPersistent("xPos", 0)
    //     .withSize(1, 1)
    //     .withPosition(3, 0)
    //     .getEntry();

    // public static NetworkTableEntry yPos = tab.addPersistent("yPos", 0)
    //     .withSize(1, 1)
    //     .withPosition(4, 0)
    //     .getEntry();

    // public static NetworkTableEntry rPos = tab.addPersistent("rPos", 0)
    //     .withSize(1, 1)
    //     .withPosition(5, 0)
    //     .getEntry();
        

    public static DoubleSupplier axisP = () -> 2.0;
    public static DoubleSupplier axisI = () -> 0.0;
    public static DoubleSupplier axisD = () -> 0.1;

    public static DoubleSupplier rotP = () -> 0.9;
    public static DoubleSupplier rotI = () -> 0.0;
    public static DoubleSupplier rotD = () -> 0.0;
    
}
