package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DigitalIO;

public class Climber extends SubsystemBase {
    private TalonFX rightTalon = new TalonFX(Constants.CLIMBER_RIGHT_FALCON);
    private TalonFX leftTalon = new TalonFX(Constants.CLIMBER_LEFT_FALCON);
    DoubleSolenoid leftSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.CLIMBER_SOLENOID_LEFT_FORWARD,
            Constants.CLIMBER_SOLENOID_LEFT_REVERSE);
    DoubleSolenoid rightSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.CLIMBER_SOLENOID_RIGHT_FORWARD,
            Constants.CLIMBER_SOLENOID_RIGHT_REVERSE);

    private DigitalIO leftLimitBottom = new DigitalIO(Constants.LEFT_CLIMB_LIMIT_BOTTOM_PORT,
            Constants.CLIMB_LIMITS_INVERTED);
    private DigitalIO leftLimitTop = new DigitalIO(Constants.LEFT_CLIMB_LIMIT_TOP_PORT,
            Constants.CLIMB_LIMITS_INVERTED);

    private DigitalIO rightLimitBottom = new DigitalIO(Constants.RIGHT_CLIMB_LIMIT_BOTTOM_PORT,
            Constants.CLIMB_LIMITS_INVERTED);
    private DigitalIO rightLimitTop = new DigitalIO(Constants.RIGHT_CLIMB_LIMIT_TOP_PORT,
            Constants.CLIMB_LIMITS_INVERTED);

    public enum ClimberPositions {
        TILTED, VERTICAL;
    }

    public Climber() {
        rightTalon.setNeutralMode(Constants.CLIMBER_IDLE_BEHAVIOR);
        leftTalon.setNeutralMode(Constants.CLIMBER_IDLE_BEHAVIOR);

        StatorCurrentLimitConfiguration limit = new StatorCurrentLimitConfiguration(true, 40, 40, 0);
        rightTalon.configStatorCurrentLimit(limit);
        leftTalon.configStatorCurrentLimit(limit);

        Constants.CLIMB_DEBUG_GROUP.addBoolean("Left Limit Bottom", this::getLeftLimitBottom);
        Constants.CLIMB_DEBUG_GROUP.addBoolean("Left Limit Top", this::getLeftLimitTop);
        Constants.CLIMB_DEBUG_GROUP.addBoolean("Right Limit Bottom", this::getRightLimitBottom);
        Constants.CLIMB_DEBUG_GROUP.addBoolean("Right Limit Top", this::getRightLimitTop);

        Shuffleboard.getTab("Climber").addNumber("Left Current", this::getLeftCurrent);
        Shuffleboard.getTab("Climber").addNumber("Right Current", this::getRightCurrent);

        rightTalon.setInverted(true);
        leftTalon.setInverted(true);

    }

    /**
     * Set the speed of the left climber.
     * 
     * @param speed The left climber speed as a percentage
     */
    public void setLeft(double speed) {
        leftTalon.set(ControlMode.PercentOutput, clamp(getLeftLimitTop(), getLeftLimitBottom(), speed, false));
    }

    /**
     * Set the speed of both climbers.
     * 
     * @param demand Climber speed as a percentage
     */
    public void setPower(double demand) {
        leftTalon.set(ControlMode.PercentOutput, demand);
        rightTalon.set(ControlMode.PercentOutput, demand);
    }

    /**
     * Set the speed of the right climber.
     * 
     * @param speed The left climber speed as a percentage
     */
    public void setRight(double speed) {
        rightTalon.set(ControlMode.PercentOutput, clamp(getRightLimitTop(), getRightLimitBottom(), speed, false));
    }

    /**
     * Sets the angle of the climbers
     * 
     * @param deploy TILTED or VERTICAL
     */
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

    /**
     * Gets the output of the bottom left limit switch
     * 
     * @return The value of the limit switch as a boolean
     */
    public boolean getLeftLimitBottom() {
        return leftLimitBottom.get();
    }

    /**
     * Gets the output of the top left limit switch
     * 
     * @return The value of the limit switch as a boolean
     */
    public boolean getLeftLimitTop() {
        return leftLimitTop.get();
    }

    /**
     * Gets the output of the bottom right limit switch
     * 
     * @return The value of the limit switch as a boolean
     */
    public boolean getRightLimitBottom() {
        return rightLimitBottom.get();
    }

    /**
     * Gets the output of the bottom left limit switch
     * 
     * @return The value of the limit switch as a boolean
     */
    public boolean getRightLimitTop() {
        return rightLimitTop.get();
    }

    public double getRightCurrent() {
        return rightTalon.getStatorCurrent();
    }

    public double getLeftCurrent() {
        return leftTalon.getStatorCurrent();
    }

    public double getRightVelocity() {
        return 0;
    }

    /*
     * Formatted by Ryan Hendrickson
     * 
     * @author Ryan Hendrickson
     * 
     * Ethan Pak felt the need to comment this :/
     * - Ryan Hendrickson
     * - Ethan Pak
     * - Tim Hendrickson's son
     * - Ethan Pak's dad's son
     * - The grandpa of Ryan Hendrickson's grandson
     */
    public double clamp(boolean limitTop, boolean limitBottom, double command, boolean overide) {
        if (overide)
            return command;
        if (limitTop && command > 0)
            return 0;
        if (limitBottom && command < 0)
            return 0;
        return command;
    }
}
