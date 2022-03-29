// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.drivetrain.DriveDirection;
import frc.robot.subsystems.drivetrain.WheelsState;

/** An example command that uses an example subsystem. */
public class DefaultDriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  private final DoubleSupplier fwdSupplier;
  private final DoubleSupplier strSupplier;
  private final DoubleSupplier rotSupplier;
  private DriveDirection driveDir;
  private WheelsState wheelsState;

  private int mode;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DefaultDriveCommand(Drivetrain drivetrain, DoubleSupplier fwd, DoubleSupplier str, DoubleSupplier rot) {
    this.drivetrain = drivetrain;
    fwdSupplier = fwd;
    strSupplier = str;
    rotSupplier = rot;
    
    addRequirements(drivetrain);

    mode = 0;
  }

  public DefaultDriveCommand(Drivetrain drivetrain, DoubleSupplier fwd, DoubleSupplier str, DoubleSupplier rot, int mode) {
    this.drivetrain = drivetrain;
    fwdSupplier = fwd;
    strSupplier = str;
    rotSupplier = rot;
    
    addRequirements(drivetrain);

    this.mode = mode;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setSpeed(0.5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveDir = new DriveDirection(
      fwdSupplier.getAsDouble(), 
      strSupplier.getAsDouble(), 
      rotSupplier.getAsDouble(), 
      drivetrain.getGyro()
    );
    
    // if (CONSTRAINT_ENABLED) {
    //   int xBarrier = xBarrier(drivetrain.getPosition());
    //   if (xBarrier == 1) {
    //     if (driveDir.getStr() > 0)
    //       driveDir.setStr(-0.05);
    //   }

    //   if (xBarrier == -1) {
    //     if (driveDir.getStr() < 0)
    //       driveDir.setStr(0.05);
    //   }


    //   int yBarrier = yBarrier(drivetrain.getPosition());
    //   if (yBarrier == 1) {
    //     if (driveDir.getFwd() > 0)
    //       driveDir.setFwd(-0.05);
    //   }

    //   if (yBarrier == -1) {
    //     if (driveDir.getFwd() < 0)
    //       driveDir.setFwd(0.05);
    //   }
    // }

    if (mode == 0)
      driveDir.zero();

    wheelsState = new WheelsState(driveDir);

    wheelsState.optimizePos(drivetrain.getPos());

    drivetrain.update(wheelsState);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void changeMode() {
    mode += 1;
    mode %= 2;
  }

  public void setMode(int mode) {
    this.mode = mode % 2;
  }

  // public int xBarrier(Position current) {
  //   if (current.getPos()[0] > X_CONSTRAINT_OFFSET)
  //     return 1;
  //   else if (current.getPos()[0] < -X_CONSTRAINT_OFFSET)
  //     return -1;
  //   return 0;
  // }

  // public int yBarrier(Position current) {
  //   if (current.getPos()[1] > Y_CONSTRAINT_OFFSET)
  //     return 1;
  //   else if (current.getPos()[1] < -Y_CONSTRAINT_OFFSET)
  //     return -1;
  //   return 0;
  // }

  public int getMode() {
    return mode;
  }
}
