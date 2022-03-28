// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.drivetrain.DriveDirection;
import frc.robot.subsystems.drivetrain.Position;
import frc.robot.subsystems.drivetrain.PositionFinder;
import frc.robot.subsystems.drivetrain.WheelsState;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class GoToCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  private PositionFinder endPos;
  private DriveDirection driveDir;
  private WheelsState wheelsState;

  private DriveDirection goDir;

  private Position pos;

  public GoToCommand(Drivetrain drivetrain, Pose2d pose) {
    this(drivetrain, Position.fromPose(pose));
  }
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public GoToCommand(Drivetrain drivetrain, Position position) {
    this.drivetrain = drivetrain;

    pos = position;
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setSpeed(0.4);
    endPos = new PositionFinder(pos, drivetrain.getPosition());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    goDir = endPos.getDirection(drivetrain.getPosition());

    driveDir = new DriveDirection(
      goDir.getFwd(),  
      goDir.getStr(), 
      goDir.getRot(), 
      drivetrain.getGyro()
    );

    driveDir.zero();

    wheelsState = new WheelsState(driveDir);

    wheelsState.optimizePos(drivetrain.getPos());

    drivetrain.update(wheelsState);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0, 0, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return endPos.isDone();
  }
}
