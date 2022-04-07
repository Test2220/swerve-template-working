package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class TunableDouble {
    private double defaultValue;
    private NetworkTableEntry shuffleboard;

    public TunableDouble(String name, double defaultValue, boolean tunable) {
        this.defaultValue = defaultValue;

        if (tunable)
            shuffleboard =         
                Shuffleboard.getTab("Tunables")
                .add(name, defaultValue)
                .getEntry();
        else
            shuffleboard = null;
    }

    public double getValue() {
        if (shuffleboard != null)
            return shuffleboard.getDouble(defaultValue);
        return defaultValue;
    }
}