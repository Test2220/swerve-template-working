package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;

public class AutomaticConveyor extends CommandBase {
    Conveyor conveyorSubsystem;
    SystemState systemState = SystemState.MANUAL;
    DoubleSupplier manualPower;
    BooleanSupplier manualOveride;
    BooleanSupplier switchToIdle;

    public AutomaticConveyor(Conveyor conveyorSubsystem, DoubleSupplier manualPower, BooleanSupplier manualOveride,
            BooleanSupplier switchToIdle) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.manualPower = manualPower;
        this.manualOveride = manualOveride;
        this.switchToIdle = switchToIdle;
        addRequirements(conveyorSubsystem);

    }

    public void initialize() {

    }

    public void execute() {
        int inRobot = conveyorSubsystem.getInRobot();
        switch (systemState) {
            case IDLE:
                conveyorSubsystem.setPower(0);
                if (conveyorSubsystem.isBallPresentAtInput()) {
                    transitionSystemState(SystemState.LOADING);
                }

                if (inRobot == 2) {
                    if (!conveyorSubsystem.isBallPresentAtShooter()) {
                        transitionSystemState(SystemState.LOADING);
                    }
                }
                if (manualOveride.getAsBoolean()) {
                    transitionSystemState(SystemState.MANUAL);
                }
                break;
            case LOADING:
                conveyorSubsystem.setPower(Constants.CONVEYOR_POWER);
                if (inRobot != 2) {
                    transitionSystemState(SystemState.IDLE);
                }

                if (manualOveride.getAsBoolean()) {
                    transitionSystemState(SystemState.MANUAL);
                }
                break;
            case MANUAL:
                conveyorSubsystem.setPower(manualPower.getAsDouble());
                if (switchToIdle.getAsBoolean()) {
                    transitionSystemState(SystemState.IDLE);
                }
                break;
        }
    }

    public enum SystemState {
        // What the robot is currently doing
        IDLE, LOADING, MANUAL;
    }

    public void transitionSystemState(SystemState state) {
        if (state == systemState) {
            return;
        }
        switch (state) {
            case IDLE:
                break;
            case LOADING:
                break;
            case MANUAL:
                break;
        }
        systemState = state;
    }

    public void end() {

    }

}
