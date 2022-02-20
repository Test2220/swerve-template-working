package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PhotoEyeSensor;

import static frc.robot.Constants.*;

public class ConveyorSubsystem extends SubsystemBase {

    PhotoEyeSensor photoEyeSensorIn = new PhotoEyeSensor(1, true);
    PhotoEyeSensor photoEyeSensorOut = new PhotoEyeSensor(2, true);
    TalonFX talon = new TalonFX(CONVEYOR_FALCON);

    public ConveyorSubsystem() {
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor In", photoEyeSensorIn::get);
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor Out", photoEyeSensorOut::get);
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
