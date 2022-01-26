package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {
    PWM m_LED = new PWM(Constants.BlinkinPWMPort);
    
    public enum Pattern {
        HOTPINK(0.57),
        GREEN(0.77),
        RED(0.61),
        BLUE(0.87),
        YELLOW(0.69),
        VIOLET(0.91);

        public final double label;

        private Pattern(double label){
            this.label = label;
        }
    }

    public void setPattern(Pattern color){
        m_LED.setSpeed(color.label);
    }

}
