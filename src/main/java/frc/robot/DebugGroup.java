package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class DebugGroup {
    ShuffleboardLayout container;

    /**
     * Builds a new DebugGroup. Creates a list of values in the "Debug" tab
     * 
     * @param name Name of tab
     * @param enabled Whether the group is active or not
     */
    public DebugGroup(String name, boolean enabled) {
        if (enabled)
            container = Shuffleboard.getTab("Debug").getLayout(name, BuiltInLayouts.kList);
        else
            container = null;
    } 

    /**
     * Adds a boolean to the DebugGroup, if the group is enabled. If it is disabled, this method is 
     * ignored
     * @param name Name of the value to add to the debug group
     * @param value BooleanSupplier for the value
     */
    public void addBoolean(String name, BooleanSupplier value) {
        if (container != null)
            container.addBoolean(name, value);
    }
}
