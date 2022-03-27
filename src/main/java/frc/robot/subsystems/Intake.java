package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    public enum Position {
        EXTENDED, RETRACTED
    }

    TalonFX talonFXLeft = new TalonFX(Constants.Intake_Talon_Left); // change the numbers once the talonFX is updated
    // TalonFX talonFXRight = new TalonFX(Constants.Intake_Talon_Right);
    DoubleSolenoid solenoidL = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.LEFT_INTAKE_SOLENOID_FORWARD,
            Constants.LEFT_INTAKE_SOLENOID_REVERSE);
    DoubleSolenoid solenoidR = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.RIGHT_INTAKE_SOLENOID_FORWARD,
            Constants.RIGHT_INTAKE_SOLENOID_REVERSE);

    public Intake() {
        // talonFXRight.follow(talonFXLeft);
        talonFXLeft.configFactoryDefault();
    }

    public void periodic() {

    }

    public void setPower(double demand) {
        talonFXLeft.set(ControlMode.PercentOutput, demand);
    }

    public double getSensorPosition() {
        return talonFXLeft.getSelectedSensorPosition();
    }

    public void setPosition(Position deploy) {
        switch (deploy) {
            case EXTENDED:
                solenoidL.set(Value.kReverse);
                solenoidR.set(Value.kReverse);
                break;
            case RETRACTED:
                solenoidL.set(Value.kForward);
                solenoidR.set(Value.kForward);
                break;
        }

    }
}