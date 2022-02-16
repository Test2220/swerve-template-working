package frc.robot;

import com.ctre.phoenix.Logger;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.ejml.dense.block.decomposition.hessenberg.TridiagonalDecompositionHouseholder_MT_FDRB;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    
    private TalonFX leftFalcon;
    private TalonFX rightFalcon;
 


    // public enum ShooterSystemState {
    //     STILL, SPINNING_UNREADY, SPINNING_READY
    // }

    public enum ShooterDesiredState {
        IDLE, SHOOT 
    }

    // private ShooterSystemState sState = ShooterSystemState.STILL;
    private ShooterDesiredState dState = ShooterDesiredState.IDLE;

    public void Shooter() {
        leftFalcon = new TalonFX(Constants.SHOOTER_TALON_LEFT);
        rightFalcon = new TalonFX(Constants.SHOOTER_TALON_RIGHT);

        leftFalcon.configFactoryDefault();
        rightFalcon.configFactoryDefault();

        leftFalcon.setInverted(Constants.LEFT_FALCON_DIRECTION);
        rightFalcon.setInverted(Constants.RIGH_FALCON_DIRECTION);

        rightFalcon.follow(leftFalcon);

    }
    public void periodic() {
        
    }

    public void setPower(double demand) {
        leftFalcon.set(TalonFXControlMode.PercentOutput, demand);
    }
    

    public void setState(ShooterDesiredState newState) {
        if(dState == newState) {
            return;
        }
        switch (newState) {
            case IDLE:
                break;
            case SHOOT:
                break;
        }
        dState = newState;
        }

        public ShooterDesiredState getDesiredState() {
            return dState;
        }
}