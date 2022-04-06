package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DigitalIO;

public class Climber extends SubsystemBase {
    private TalonFX rightTalon = new TalonFX(Constants.CLIMBER_RIGHT_FALCON);
    private TalonFX leftTalon = new TalonFX(Constants.CLIMBER_LEFT_FALCON);
    DoubleSolenoid leftSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID_LEFT_FORWARD, 
    Constants.CLIMBER_SOLENOID_LEFT_REVERSE);
    DoubleSolenoid rightSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID_RIGHT_FORWARD, 
    Constants.CLIMBER_SOLENOID_RIGHT_REVERSE);

    private DigitalIO leftLimitBottom = new DigitalIO(Constants.LEFT_CLIMB_LIMIT_BOTTOM_PORT, Constants.CLIMB_LIMITS_INVERTED);
    private DigitalIO leftLimitTop = new DigitalIO(Constants.LEFT_CLIMB_LIMIT_TOP_PORT, Constants.CLIMB_LIMITS_INVERTED);

    private DigitalIO rightLimitBottom = new DigitalIO(Constants.RIGHT_CLIMB_LIMIT_BOTTOM_PORT, Constants.CLIMB_LIMITS_INVERTED);
    private DigitalIO rightLimitTop = new DigitalIO(Constants.RIGHT_CLIMB_LIMIT_TOP_PORT, Constants.CLIMB_LIMITS_INVERTED);


    public enum ClimberPositions {
        TILTED, VERTICAL;
    }

    public Climber() {
        rightTalon.setNeutralMode(Constants.CLIMBER_IDLE_BEHAVIOR);
        leftTalon.setNeutralMode(Constants.CLIMBER_IDLE_BEHAVIOR);
        // rightTalon.configForwardSoftLimitThreshold(X);
        // rightTalon.configReverseSoftLimitThreshold(0);
        // Shuffleboard.getTab("Climber").addNumber("Right Sensor Units", rightTalon::getSelectedSensorPosition);
        // Shuffleboard.getTab("Climber").addNumber("Left Sensor Units", leftTalon::getSelectedSensorPosition);
        ShuffleboardTab tab = Shuffleboard.getTab("Climber");
        tab.addBoolean("Left Limit Bottom", this::getLeftLimitBottom);
        tab.addBoolean("Left Limit Top", this::getLeftLimitTop);
        tab.addBoolean("Right Limit Bottom", this::getRightLimitBottom);
        tab.addBoolean("Right Limit Top", this::getRightLimitTop);

        rightTalon.setInverted(true);
        leftTalon.setInverted(true);

    }

    public void setLeft(double speed) {
        leftTalon.set(ControlMode.PercentOutput, speed);
    }

    public void setPower(double demand) {
        leftTalon.set(ControlMode.PercentOutput, demand);
        rightTalon.set(ControlMode.PercentOutput, demand);
    }

    public void setRight(double speed) {
        rightTalon.set(ControlMode.PercentOutput, speed);
    }

    public void setPosition(ClimberPositions deploy) {
        switch (deploy) {
            case TILTED:
            leftSolenoid.set(Value.kForward);
            rightSolenoid.set(Value.kForward);
            break;
            case VERTICAL:
            leftSolenoid.set(Value.kReverse);
            rightSolenoid.set(Value.kReverse);
            break;
        }
    }

    public boolean getLeftLimitBottom() {
        return leftLimitBottom.get();
    }

    public boolean getLeftLimitTop() {
        return leftLimitTop.get();
    }

    public boolean getRightLimitBottom() {
        return rightLimitBottom.get();
    }

    public boolean getRightLimitTop() {
        return rightLimitTop.get();
    }

    /*
     * Formatted by Ryan Hendrickson
     * @author Ryan Hendrickson
     * 
     * Ethan Pak felt the need to comment this :/
     * - Ryan Hendrickson
     *  - Ethan Pak
     *   - Tim Hendrickson's son
     *    - Ethan Pak's dad's son
     *     - The grandpa of Ryan Hendrickson's grandson
     */
    public double clamp(boolean limitTop, boolean limitBottom, double command, boolean overide) {
        if (overide)                    return command;

        if (limitTop && command > 0)    return 0; 
        if (limitBottom && command < 0) return 0;

                                        return command;
    }
}
