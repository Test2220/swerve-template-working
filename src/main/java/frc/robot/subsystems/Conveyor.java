package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
// import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

// import com.revrobotics.ColorSensorV3;
// import com.revrobotics.ColorMatchResult;
// import com.revrobotics.ColorMatch;
// import edu.wpi.first.wpilibj.util.Color;

import frc.robot.DigitalIO;

import static frc.robot.Constants.*;

// import java.util.Map;

//Conveyor.java is fine, but it has lots of errors and doesn't build on Mac. On all other devices there shouldn't be any issues.

public class Conveyor extends SubsystemBase {

    DigitalIO photoEyeSensorIn = new DigitalIO(PHOTOEYE_SENSOR_INTAKE, true);
    DigitalIO photoEyeSensorOut = new DigitalIO(PHOTOEYE_SENSOR_LAUNCHER, true);
    TalonFX talon = new TalonFX(CONVEYOR_FALCON);
    int inRobot = 0;
    boolean ballInput = false;
    boolean ballOutput = false;

    // boolean ballOneColorRed = false;
    // boolean ballOneColorBlue = false;
    // boolean ballTwoColorRed = false;
    // boolean ballTwoColorBlue = false;

    // boolean ballColorInput = false;

    // private final Color blue = new Color(0.143, 0.427, 0.429);
    // private final Color red = new Color(0.561, 0.232, 0.114);

    // private final I2C.Port i2cPort = I2C.Port.kOnboard;
    // private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    // private final ColorMatch m_colorMatcher = new ColorMatch();

    // int colorDistance = 0;

    

    public Conveyor() {

        // Shuffleboard.getTab("Conveyor").addBoolean("Sensor In", photoEyeSensorIn::get);
        // Shuffleboard.getTab("Conveyor").addBoolean("Sensor Out", photoEyeSensorOut::get);
        // Shuffleboard.getTab("Conveyor").addNumber("In Robot", () -> inRobot);
        // Shuffleboard.getTab("Conveyor").addBoolean("Ball 1 Color Red", () -> ballOneColorRed);
        //      //   .withProperties(Map.of("colorWhenTrue", "red", "colorWhenFalse", "black"));

        // Shuffleboard.getTab("Conveyor").addBoolean("Ball 1 Color Blue", () -> ballOneColorBlue);
        //       //  .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black"));

        // Shuffleboard.getTab("Conveyor").addBoolean("Ball 2 Color Red", () -> ballTwoColorRed);
        //     //    .withProperties(Map.of("colorWhenTrue", "red", "colorWhenFalse", "black"));

        // Shuffleboard.getTab("Conveyor").addBoolean("Ball 2 Color Blue", () -> ballTwoColorBlue);
        //     //    .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black"));

        // Shuffleboard.getTab("Conveyor").addNumber("Detected Color Distance", () -> colorDistance);

        talon.setInverted(true);

        talon.configVoltageCompSaturation(10);
        talon.enableVoltageCompensation(true);

        
        talon.configVoltageCompSaturation(10);
        talon.enableVoltageCompensation(false);

        talon.configNeutralDeadband(0.001);

        final int TIMEOUTMS = 30;
        talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, TIMEOUTMS);

        talon.configNominalOutputForward(0, TIMEOUTMS);
        talon.configNominalOutputReverse(0, TIMEOUTMS);
        talon.configPeakOutputForward(1, TIMEOUTMS);
        talon.configPeakOutputReverse(-1, TIMEOUTMS);

        talon.config_kF(0, Constants.PIDSHOOTER_F, TIMEOUTMS);
        talon.config_kP(0, Constants.PIDSHOOTER_P, TIMEOUTMS);
        talon.config_kI(0, Constants.PIDSHOOTER_I, TIMEOUTMS);
        talon.config_kD(0, Constants.PIDSHOOTER_D, TIMEOUTMS);

        Constants.CONVEYOR_DEBUG_GROUP.addBoolean("IN", photoEyeSensorIn::get);
        Constants.CONVEYOR_DEBUG_GROUP.addBoolean("OUT", photoEyeSensorOut::get);

        Shuffleboard.getTab("Shooter")
            .addNumber("CONVEYOR_RPM", this::getVelocity);
    }

    /**
     * Sets the power of the conveyor
     * @param power Power as a double
     */
    public void setPower(double power) {
        talon.set(ControlMode.PercentOutput, power);
    }

    /**
     * Sets the velocity of the conveyor
     * @param speed Velocity as a double
     */
    public void setSpeed(double speed) {
        talon.set(ControlMode.Velocity, speed);
    }

    /**
     * Get whether a ball is present at the input
     * @return Bottom PhotoEye sensor as a boolean
     */
    public boolean isBallPresentAtInput() {
        return photoEyeSensorIn.get();

    }

    /**
     * Gets whether a ball is present at the output
     * @return Top PhotoEye sensor as a boolean
     */
    public boolean isBallPresentAtShooter() {
        return photoEyeSensorOut.get();
    }

    /**
     * Gets how many balls are currently in the robot
     * @return The number of balls in the robot as an int
     */
    @Deprecated
    public int getInRobot() {
        return inRobot;
    }

    @Override
    public void periodic() {
        if (isBallPresentAtInput()) {
            if (!ballInput) {
                // inRobot++;
                // Color detectedColor = m_colorSensor.getColor();

                // if (detectedColor == red) {
                //     ballTwoColorRed = ballOneColorRed;
                //     ballTwoColorBlue = ballOneColorBlue;
                //     ballOneColorBlue = false;
                //     ballOneColorRed = true;
                // } else if (detectedColor == blue) {
                //     ballTwoColorRed = ballOneColorRed;
                //     ballTwoColorBlue = ballOneColorBlue;
                //     ballOneColorBlue = true;
                //     ballOneColorRed = false;
                // }
                ballInput = true;
            }
        } else {
            ballInput = false;
        }

        if (isBallPresentAtShooter()) {
            if (!ballOutput) {
                // inRobot--;
                ballOutput = true;
            }
        } else {
            ballOutput = false;
        }

        // Color detectedColor = m_colorSensor.getColor();
        // colorDistance = m_colorSensor.getProximity();
        // if (ballColorInput) {
        //     if (detectedColor == red) {
        //         ballTwoColorRed = ballOneColorRed;
        //         ballTwoColorBlue = ballOneColorBlue;
        //         ballOneColorBlue = false;
        //         ballOneColorRed = true;
        //     } else if (detectedColor == blue) {
        //         ballTwoColorRed = ballOneColorRed;
        //         ballTwoColorBlue = ballOneColorBlue;
        //         ballOneColorBlue = true;
        //         ballOneColorRed = false;
        //     } else {
        //         ballColorInput = false;
        //     }
        // }

    }

    public double getVelocity() {
        return talon.getSelectedSensorVelocity() / 2048.0 * 600;
    }
}