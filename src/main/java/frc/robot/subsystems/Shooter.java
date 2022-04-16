package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.TunableDouble;

public class Shooter extends SubsystemBase {
    private TunableDouble lowGoal = new TunableDouble("Low Goal RPM", 2000, true);
    private TunableDouble highGoal = new TunableDouble("High Goal RPM", 4000, true);
    private TunableDouble launchGoal = new TunableDouble("Launch Goal RPM", 6600, true);
    private TunableDouble toleranceHigh = new TunableDouble("Shooter High Tolerence", 250, true);
    private TunableDouble toleranceLaunch = new TunableDouble("Shooter Launch Tolerance", 2800, true);
    private TalonFX leftFalcon;
    

    // private TalonFX rightFalcon;
 


    // public enum ShooterSystemState {
    //     STILL, SPINNING_UNREADY, SPINNING_READY
    // }

    public enum ShooterDesiredState {
        IDLE, SHOOT 
    }

    // private ShooterSystemState sState = ShooterSystemState.STILL;
    private ShooterDesiredState dState = ShooterDesiredState.IDLE;

    public Shooter() {
        leftFalcon = new TalonFX(Constants.SHOOTER_TALON_LEFT);
        // rightFalcon = new TalonFX(Constants.SHOOTER_TALON_RIGHT);

        leftFalcon.configFactoryDefault();
        // rightFalcon.configFactoryDefault();

        leftFalcon.setInverted(Constants.LEFT_FALCON_DIRECTION);
        // rightFalcon.setInverted(Constants.RIGH_FALCON_DIRECTION);

        // rightFalcon.follow(leftFalcon);

        leftFalcon.configVoltageCompSaturation(10);
        leftFalcon.enableVoltageCompensation(false);

        leftFalcon.configNeutralDeadband(0.001);

        final int TIMEOUTMS = 30;
        leftFalcon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, TIMEOUTMS);

        leftFalcon.configNominalOutputForward(0, TIMEOUTMS);
        leftFalcon.configNominalOutputReverse(0, TIMEOUTMS);
        leftFalcon.configPeakOutputForward(1, TIMEOUTMS);
        leftFalcon.configPeakOutputReverse(-1, TIMEOUTMS);

        leftFalcon.config_kF(0, Constants.PIDSHOOTER_F, TIMEOUTMS);
        leftFalcon.config_kP(0, Constants.PIDSHOOTER_P, TIMEOUTMS);
        leftFalcon.config_kI(0, Constants.PIDSHOOTER_I, TIMEOUTMS);
        leftFalcon.config_kD(0, Constants.PIDSHOOTER_D, TIMEOUTMS);

        Shuffleboard.getTab("Shooter")
            .addNumber("SHOOTER_RPM", leftFalcon::getClosedLoopError);

    }
    public void periodic() {
        
    }

    public void setVoltageComp(boolean enabled) {
        leftFalcon.enableVoltageCompensation(enabled);
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

    public void setLaunchpadVelocity() {
        double targetVelocity_UnitsPer100ms = launchGoal.getValue() * 2048.0 / 600.0;
        leftFalcon.set(TalonFXControlMode.Velocity , targetVelocity_UnitsPer100ms);
    }

    public void setHighVelocity() {
        double targetVelocity_UnitsPer100ms = highGoal.getValue() * 2048.0 / 600.0;
        leftFalcon.set(TalonFXControlMode.Velocity , targetVelocity_UnitsPer100ms);
    }
    public void setLowVelocity() {
        double targetVelocity_UnitsPer100ms =  lowGoal.getValue() * 2048.0 / 600.0;
        leftFalcon.set(TalonFXControlMode.Velocity , targetVelocity_UnitsPer100ms);
    }    

    public double getPIDError() {
        return leftFalcon.getClosedLoopError();
    }

    public boolean withinHighTolerance() {
        return (leftFalcon.getClosedLoopError() < toleranceHigh.getValue());
    }

    public boolean withinLaunchTolerance() {
        return (leftFalcon.getClosedLoopError() < toleranceLaunch.getValue());
    }
}