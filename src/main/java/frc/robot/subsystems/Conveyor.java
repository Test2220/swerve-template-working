package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PhotoEyeSensor;

import static frc.robot.Constants.*;

public class Conveyor extends SubsystemBase {

    PhotoEyeSensor photoEyeSensorIn = new PhotoEyeSensor(PHOTOEYE_SENSOR_INTAKE, true);
    PhotoEyeSensor photoEyeSensorOut = new PhotoEyeSensor(PHOTOEYE_SENSOR_LAUNCHER, true);
    TalonFX talon = new TalonFX(CONVEYOR_FALCON);

    public Conveyor() {
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor In", photoEyeSensorIn::get);
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor Out", photoEyeSensorOut::get);

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

}
