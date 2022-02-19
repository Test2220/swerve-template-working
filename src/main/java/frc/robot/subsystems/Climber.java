package frc.robot.subsystems;

import javax.security.auth.x500.X500Principal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private TalonFX rightTalon = new TalonFX(Constants.CLIMBER_RIGHT_FALCON);
    private TalonFX leftTalon = new TalonFX(Constants.CLIMBER_LEFT_FALCON);
    DoubleSolenoid leftSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID_LEFT_FORWARD, 
    Constants.CLIMBER_SOLENOID_LEFT_REVERSE);
    DoubleSolenoid rightSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID_RIGHT_FORWARD, 
    Constants.CLIMBER_SOLENOID_RIGHT_REVERSE);


    public enum ClimberPositions {
        TILTED, VERTICAL;
    }

    public Climber() {
        rightTalon.setNeutralMode(Constants.CLIMBER_IDLE_BEHAVIOR);
        rightTalon.follow(leftTalon);
        // rightTalon.configForwardSoftLimitThreshold(X);
        // rightTalon.configReverseSoftLimitThreshold(0);
        Shuffleboard.getTab("Climber").addNumber("Right Sensor Units", rightTalon::getSelectedSensorPosition);
        Shuffleboard.getTab("Climber").addNumber("Left Sensor Units", leftTalon::getSelectedSensorPosition);

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

    
}
