package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Intake extends SubsystemBase {
    public enum Position{
        EXTENDED, RETRACTED
    }
    TalonFX talonFXLeft = new TalonFX(Constants.Intake_Talon_Left); //change the numbers once the talonFX is updated 
    TalonFX talonFXRight = new TalonFX(Constants.Intake_Talon_Right);
    DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_SOLENOID_FORWARD, 
    Constants.INTAKE_SOLENOID_REVERSE);    
   
    public Intake() {
       talonFXRight.follow(talonFXLeft);
       talonFXLeft.configFactoryDefault();
    }

    public void periodic() {

    }

    public void setPower(double demand) {
        talonFXLeft.set(ControlMode.PercentOutput, demand);
    }

    public void setPosition(Position deploy) {
        switch (deploy) {
            case EXTENDED:
            solenoid.set(Value.kForward);
            break;
            case RETRACTED:
            solenoid.set(Value.kReverse);
            break;
        }

        }
    }