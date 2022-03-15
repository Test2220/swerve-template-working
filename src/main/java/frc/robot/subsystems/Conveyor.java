package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.PhotoEyeSensor;

import static frc.robot.Constants.*;

import java.util.Map;

//Conveyor.java is fine, but it has lots of errors and doesn't build on Mac. On all other devices there shouldn't be any issues.

public class Conveyor extends SubsystemBase {

    PhotoEyeSensor photoEyeSensorIn = new PhotoEyeSensor(PHOTOEYE_SENSOR_INTAKE, true);
    PhotoEyeSensor photoEyeSensorOut = new PhotoEyeSensor(PHOTOEYE_SENSOR_LAUNCHER, true);
    TalonFX talon = new TalonFX(CONVEYOR_FALCON);
    int inRobot = 0;
    boolean ballInput = false;
    boolean ballOutput = false;

    boolean ballOneColorRed = false;
    boolean ballOneColorBlue = false;
    boolean ballTwoColorRed = false;
    boolean ballTwoColorBlue = false;

    boolean ballColorInput = false;

    private final Color blue = new Color(0.143, 0.427, 0.429);
    private final Color red = new Color(0.561, 0.232, 0.114);

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();

    int colorDistance = 0;

    

    public Conveyor() {

        Shuffleboard.getTab("Conveyor").addBoolean("Sensor In", photoEyeSensorIn::get);
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor Out", photoEyeSensorOut::get);
        Shuffleboard.getTab("Conveyor").addNumber("In Robot", () -> inRobot);
        Shuffleboard.getTab("Conveyor").addBoolean("Ball 1 Color Red", () -> ballOneColorRed)
                .withProperties(Map.of("colorWhenTrue", "red", "colorWhenFalse", "black"));

        Shuffleboard.getTab("Conveyor").addBoolean("Ball 1 Color Blue", () -> ballOneColorBlue)
                .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black"));

        Shuffleboard.getTab("Conveyor").addBoolean("Ball 2 Color Red", () -> ballTwoColorRed)
                .withProperties(Map.of("colorWhenTrue", "red", "colorWhenFalse", "black"));

        Shuffleboard.getTab("Conveyor").addBoolean("Ball 2 Color Blue", () -> ballTwoColorBlue)
                .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black"));

        Shuffleboard.getTab("Conveyor").addNumber("Detected Color Distance", () -> colorDistance);

        talon.setInverted(true);
    }

    public void setPower(double power) {
        talon.set(ControlMode.PercentOutput, power);
    }

    public boolean isBallPresentAtInput() {
        return photoEyeSensorIn.get();

    }

    public boolean isBallPresentAtShooter() {
        return photoEyeSensorOut.get();
    }

    public int getInRobot() {
        return inRobot;
    }

    @Override
    public void periodic() {
        if (isBallPresentAtInput()) {
            if (!ballInput) {
                inRobot++;
                Color detectedColor = m_colorSensor.getColor();

                if (detectedColor == red) {
                    ballTwoColorRed = ballOneColorRed;
                    ballTwoColorBlue = ballOneColorBlue;
                    ballOneColorBlue = false;
                    ballOneColorRed = true;
                } else if (detectedColor == blue) {
                    ballTwoColorRed = ballOneColorRed;
                    ballTwoColorBlue = ballOneColorBlue;
                    ballOneColorBlue = true;
                    ballOneColorRed = false;
                }
                ballInput = true;
            }
        } else {
            ballInput = false;
        }

        if (isBallPresentAtShooter()) {
            if (!ballOutput) {
                inRobot--;
                ballOutput = true;
            }
        } else {
            ballOutput = false;
        }

        Color detectedColor = m_colorSensor.getColor();
        colorDistance = m_colorSensor.getProximity();
        if (ballColorInput) {
            if (detectedColor == red) {
                ballTwoColorRed = ballOneColorRed;
                ballTwoColorBlue = ballOneColorBlue;
                ballOneColorBlue = false;
                ballOneColorRed = true;
            } else if (detectedColor == blue) {
                ballTwoColorRed = ballOneColorRed;
                ballTwoColorBlue = ballOneColorBlue;
                ballOneColorBlue = true;
                ballOneColorRed = false;
            } else {
                ballColorInput = false;
            }
        }

    }

}