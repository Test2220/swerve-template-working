package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;

public class AutoConveyor extends CommandBase {
    Conveyor conveyor;
   

    public AutoConveyor(Conveyor conveyor) {
        this.conveyor = conveyor;
        
        addRequirements(conveyor);

    }

    public void execute() {
     
            if (conveyor.isBallPresentAtInput()) 
            {
                conveyor.setPower(Constants.SHUFFLEBOARD_CONVEYOR_SPEED.getDouble(Constants.CONVEYOR_POWER));
            } 
            else 
            {
                conveyor.setPower(0);
            }
        }

    

    public void end(boolean interrupted)
    {
        conveyor.setPower(0);
    }
}