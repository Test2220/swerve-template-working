package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class AutomaticConveyor extends CommandBase {
    ConveyorSubsystem conveyorSubsystem;
    SystemState systemState = SystemState.IDLE;

    public AutomaticConveyor(ConveyorSubsystem conveyorSubsystem) {
        this.conveyorSubsystem = conveyorSubsystem;
    }

    public void initialize() {

    }

    public void execute() {
        switch (systemState) {
            case IDLE:
                conveyorSubsystem.setPower(0);
                if (conveyorSubsystem.isBallPresentAtInput()) {
                    transitionSystemState(SystemState.LOADING);
                }
                break;
            case LOADING:
                conveyorSubsystem.setPower(0.5);
                if (!conveyorSubsystem.isBallPresentAtInput()) {
                    transitionSystemState(SystemState.IDLE);
                }
                break;
        }
    }

    public enum SystemState {
        // What the robot is currently doing
        IDLE, LOADING;
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
        }
        systemState = state;
    }

    public void end() {
        
    }

}
