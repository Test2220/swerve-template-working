package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LED.Pattern;

public class AllianceLEDs extends CommandBase {
    private final LED m_ledcommands;

    public AllianceLEDs(LED ledcommands) {
        this.m_ledcommands = ledcommands;

        addRequirements(ledcommands);
    }

    @Override
    public void initialize() {
        switch(DriverStation.getAlliance()){
            case Red:
            m_ledcommands.setPattern(Pattern.RED);
            break;
            case Blue:
            m_ledcommands.setPattern(Pattern.BLUE);
            break;
            case Invalid:
            m_ledcommands.setPattern(Pattern.YELLOW);
            break;
        }

    }
}


