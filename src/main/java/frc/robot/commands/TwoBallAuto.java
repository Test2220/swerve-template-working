package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Trajectories;

public class TwoBallAuto extends SequentialCommandGroup {
    
    public TwoBallAuto(Intake intake, DrivetrainSubsystem drivetrainSubsystem, Shooter shooter, ConveyorSubsystem conveyor){
        addCommands(
            new ExtendIntake(intake),

            new FollowPath(Trajectories.twoBall, drivetrainSubsystem).raceWith(new RunIntake(intake)),

            new RunShooter(shooter, conveyor).withTimeout(5)
        );
    }

}
