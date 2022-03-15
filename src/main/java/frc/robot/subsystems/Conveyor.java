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
    int inRobot = 0;
    boolean ballInput = false;
    boolean ballOutput = false;

    public Conveyor() {
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor In", photoEyeSensorIn::get);
        Shuffleboard.getTab("Conveyor").addBoolean("Sensor Out", photoEyeSensorOut::get);
        Shuffleboard.getTab("Conveyor").addNumber("In Robot", () -> inRobot);
        // Shuffleboard.getTab("Conveyor").addBo

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

    @Override
    public void periodic(){
        if (isBallPresentAtInput()) {
            if (!ballInput) {
                inRobot++;
                ballInput = true;
            }
        } else {
            ballInput = false;
        }

        if (isBallPresentAtShooter()) {
            if (!ballOutput) {
                inRobot--;
                ballOutput = true;
            }
        } else {
            ballOutput = false;
        }

        
    }

}
