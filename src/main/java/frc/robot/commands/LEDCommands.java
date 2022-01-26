package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LED.Pattern;

public class LEDCommands extends CommandBase {

    private final LED m_ledcommands;
    private final Pattern m_color;

    public LEDCommands(LED ledcommands, Pattern color) {
        this.m_ledcommands = ledcommands;
        this.m_color = color;

        addRequirements(ledcommands);
    }

    @Override
    public void initialize() {
        m_ledcommands.setPattern(m_color);
    }
}
