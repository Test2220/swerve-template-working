package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class TunableDouble {
    private double defaultValue;
    private NetworkTableEntry shuffleboard;

    /**
     * Creates a TunableDouble. It can be enabled and disabled (Use defaultValue)
     * 
     * @param name
     * @param defaultValue
     * @param tunable
     */
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

    /**
     * @return Value as a double
     */
    public double getValue() {
        if (shuffleboard != null)
            return shuffleboard.getDouble(defaultValue);
        return defaultValue;
    }
}