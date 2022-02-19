package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private TalonFX rightTalon = new TalonFX(Constants.CLIMBER_RIGHT_FALCON);
    private TalonFX leftTalon = new TalonFX(Constants.CLIMBER_LEFT_FALCON);
    DoubleSolenoid left_solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID_LEFT_FORWARD, 
    Constants.CLIMBER_SOLENOID_LEFT_REVERSE);
    DoubleSolenoid right_Solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CLIMBER_SOLENOID_RIGHT_FORWARD, 
    Constants.CLIMBER_SOLENOID_RIGHT_REVERSE);


    public enum ClimberPositions {
        FORWARD, BACKWARD, MOVE_UP, MOVE_DOWN;
    }

    public Climber() {
        rightTalon.setNeutralMode(Constants.CLIMBER_IDLE_BEHAVIOR);
        rightTalon.follow(leftTalon);

    }

    public void setLeft(double speed) {
        leftTalon.set(ControlMode.PercentOutput, speed);
    }

    public void setPower(double demand) {
        leftTalon.set(ControlMode.PercentOutput, demand);
    }

    public void setRight(double speed) {
        leftTalon.set(ControlMode.PercentOutput, speed);
    }

    public void setPosition(ClimberPositions deploy) {
        switch (deploy) {
            case FORWARD:
            break;
            case BACKWARD:
            break;
        }

    
    }

    
}
