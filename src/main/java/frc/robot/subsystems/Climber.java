package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private TalonFX rightTalon = new TalonFX(Constants.RIGHT_FALCON);
    private TalonFX leftTalon = new TalonFX(Constants.LEFT_FALCON);

    public enum ClimberPositions {
        FORWARD, BACKWARD, MOVE_UP, MOVE_DOWN;
    }

    public Climber() {
        rightTalon.setNeutralMode(Constants.IDLE_BEHAVIOR);
    }

    public void setLeft(double speed) {
        leftTalon.set(ControlMode.PercentOutput, speed);
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
            case MOVE_UP:
            break;
            case MOVE_DOWN:
            break;
        }

    
    }

    
}
