// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.subsystems.Conveyor;
// import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.Shooter;
// import frc.robot.subsystems.Trajectories;

// public class TerminalTwoBallAuto extends SequentialCommandGroup {
    
//     public TerminalTwoBallAuto(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
//         addCommands(
//             new RunShooter(shooter, conveyor, true).withTimeout(2),

//             new ExtendIntake(intake),

//             new FollowPath(Trajectories.terminalTwoBall, drivetrain).raceWith(new RunIntake(intake, false)),

//             new FollowPath(Trajectories.terminalTwoBallBackward, drivetrain),

//             new RunShooter(shooter, conveyor, true).withTimeout(4)
//         );
//     }
// }