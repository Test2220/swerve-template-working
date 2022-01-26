package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {
    PWM m_LED = new PWM(Constants.BlinkinPWMPort);
    public double m_lastBrownOut = 0.0;
    Pattern m_lastPattern = Pattern.BLACK;
    
    public enum Pattern {
        HOTPINK(0.57),
        GREEN(0.77),
        RED(0.61),
        BLUE(0.87),
        YELLOW(0.69),
        VIOLET(0.91),
        BLACK(0.99),
        RAINBOWWITHGLITTER(-0.89);

        public final double label;

        private Pattern(double label){
            this.label = label;
        }
    }

    public void setPattern(Pattern color){
        m_LED.setSpeed(color.label);
        m_lastPattern = color;
    }

    @Override
    public void periodic() {
        if(RobotController.isBrownedOut()) {
            m_lastBrownOut = Timer.getFPGATimestamp();
            
        }
        else if(m_lastBrownOut + 5 >= Timer.getFPGATimestamp()){
            m_LED.setSpeed(Pattern.RAINBOWWITHGLITTER.label);
        }
        else{
            setPattern(m_lastPattern);
        }
    }

}
