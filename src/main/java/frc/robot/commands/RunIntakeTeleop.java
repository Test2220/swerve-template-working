package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.Position;

public class RunIntakeTeleop extends CommandBase {
        private Intake intake;
        private boolean reverse;
        public RunIntakeTeleop(Intake intake, boolean reverse){
            this.intake = intake;
            this.reverse = reverse;
            addRequirements(intake);
        }
    
        public void intialize() {
            intake.setPosition(Position.EXTENDED);

        }
        public void execute() {
            if (reverse)
                intake.setPower(Constants.REVERSE_INTAKE_POWER);
            else
                intake.setPower(Constants.INTAKE_POWER);
        }
    
        @Override
        public void end(boolean interupted) {
            intake.setPower(0);
            intake.setPosition(Position.RETRACTED);
        }
    }
    

