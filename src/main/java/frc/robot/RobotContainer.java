// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.AllianceLEDs;
import frc.robot.commands.AutoRampPowerIntake;
import frc.robot.commands.AutomaticConveyor;
import frc.robot.commands.DefaultClimber;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.FollowPath;
import frc.robot.commands.LimelightAutoTurning;
import frc.robot.commands.LimelightDefaultCommand;
import frc.robot.commands.PPFollowPath;
import frc.robot.commands.PixyCamAutoTurning;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunIntakeTeleop;
import frc.robot.commands.RunShooter;
import frc.robot.commands.TerminalTwoBallAuto;
import frc.robot.commands.TiltClimber;
import frc.robot.commands.HangarTwoBallAuto;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.ClimberPositions;
import frc.robot.subsystems.Conveyor;
//import frc.robot.commands.PixyCamAutoTurning;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.PixyCamSPI;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Trajectories;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XboxController driverController = new XboxController(0);
  private final XboxController manipulatorController = new XboxController(1);

  private final LED led = new LED();

  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Conveyor conveyor = new Conveyor();
  private final Climber climber = new Climber();

  private final Limelight limelight = new Limelight();
  private final PixyCamSPI pixy = new PixyCamSPI(0);
  private final PowerDistribution powerDistribution = new PowerDistribution();

  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    ShuffleboardTab xbox = Shuffleboard.getTab("Xbox");
    xbox.addNumber("Left X", () -> {
      return driverController.getLeftX();
    }).withWidget(BuiltInWidgets.kGraph);

    xbox.addNumber("Left Y", () -> {
      return driverController.getLeftY();
    }).withWidget(BuiltInWidgets.kGraph);

    xbox.addNumber("Right X", () -> {
      return driverController.getRightX();
    }).withWidget(BuiltInWidgets.kGraph);
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    drivetrain.setDefaultCommand(new DefaultDriveCommand(
        drivetrain,
        () -> -modifyAxis(driverController.getLeftY()),
        () -> -modifyAxis(driverController.getLeftX()),
        () -> -modifyAxis(driverController.getRightX())));

    led.setDefaultCommand(new AllianceLEDs(led));

    limelight.setDefaultCommand(new LimelightDefaultCommand(limelight));

    conveyor.setDefaultCommand(new AutomaticConveyor(conveyor,
        () -> {
          if (manipulatorController.getPOV() == 0) {
            return Constants.CONVEYOR_POWER;
          } else if (manipulatorController.getPOV() == 180) {
            return -Constants.CONVEYOR_POWER;
          } else {
            return 0;
          }
        },
        () -> manipulatorController.getBackButton(),
        () -> manipulatorController.getStartButton()));

    climber.setDefaultCommand(new DefaultClimber(climber, 
        () -> -modifyAxis(manipulatorController.getLeftY()),
        () -> -modifyAxis(manipulatorController.getRightY())
    ));

    // Configure the button bindings
    configureButtonBindings();

    Command m_2ballAuto = new HangarTwoBallAuto(intake, drivetrain, shooter, conveyor);
    autoChooser.setDefaultOption("2 Ball Auto", m_2ballAuto);
    autoChooser.addOption("Test", new PPFollowPath(Trajectories.testTrajectory, drivetrain));
    autoChooser.addOption("Terminal Two Ball Auto", new TerminalTwoBallAuto(intake, drivetrain, shooter, conveyor));
    Shuffleboard.getTab("Auto").add("Auto", autoChooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Back button zeros the gyroscope
    new Button(driverController::getBackButton)
        // No requirements because we don't need to interrupt anything
        .whenPressed(() -> drivetrain.zeroGyroscope());

    new Button(driverController::getLeftBumper)
        .whenPressed(() -> 
            drivetrain.setSpeedModifier(.5)
        );
    new Button(driverController::getRightBumper)
        .whenPressed(() ->
            drivetrain.setSpeedModifier(1)
        );
    new Button(driverController::getStartButton)
        .whenPressed(() ->
            drivetrain.setSpeedModifier(0)
        );

    new Button(driverController::getYButton)
        .whileHeld(
            new LimelightAutoTurning(
                (output) -> {
                  drivetrain.drive(
                      -modifyAxis(driverController.getLeftY()),
                      -modifyAxis(driverController.getLeftX()),
                      output,
                      true);
                },
                limelight, drivetrain));

    new Button(driverController::getAButton).whileHeld(new PixyCamAutoTurning(
        (output) -> {
          drivetrain.drive(
              -modifyAxis(driverController.getLeftY()),
              -modifyAxis(driverController.getLeftX()),
              output,
              false);
        },
        pixy, Pixy2CCC.CCC_SIG1, drivetrain));

    new Button(driverController::getBButton).whileHeld(new PixyCamAutoTurning(
        (output) -> {
          drivetrain.drive(
              -modifyAxis(driverController.getLeftY()),
              -modifyAxis(driverController.getLeftX()),
              output,
              false);
        },
        pixy, Pixy2CCC.CCC_SIG2, drivetrain));

    new Button(driverController::getXButton).whileHeld(new PixyCamAutoTurning(
        (output) -> {
          double forward = 0.2;
          if (pixy.getSeesTarget() != true) {
            forward = 0;
            output = 0.2;
          }
          drivetrain.drive(
              forward,
              0,
              output,
              true);
        },
        pixy, Pixy2CCC.CCC_SIG1, drivetrain));
    
    new Button(()-> driverController.getPOV() == 0).whileHeld(new AutoRampPowerIntake(intake, false));

      new Button(() -> driverController.getLeftTriggerAxis() > 0.4)
      .whenPressed(
        () -> drivetrain.setFieldRelative(false)
      );

      new Button(() -> driverController.getRightTriggerAxis() > 0.4)
      .whenPressed(
        () -> drivetrain.setFieldRelative(true)
      );

    // run intake buttons
    // new Button(manipulatorController::getAButton)
    //     .whenPressed(new ExtendIntake(intake));

    // new Button(manipulatorController::getBButton)
    //     .whenPressed(new RetractIntake(intake));

    new Button(() -> manipulatorController.getRightTriggerAxis() > 0.4)
        .whileHeld(new RunShooter(shooter, conveyor, true));

    new Button(() -> manipulatorController.getLeftTriggerAxis() > 0.4)
        .whileHeld(new RunIntakeTeleop(intake, false));

    new Button(manipulatorController::getLeftBumper)
        .whileHeld(new RunIntakeTeleop(intake, true));

    new Button(manipulatorController::getRightBumper)
      .whileHeld(new RunShooter(shooter, conveyor, false));

    new Button(manipulatorController::getXButton).whenPressed(new TiltClimber(climber, ClimberPositions.TILTED));

    new Button(manipulatorController::getYButton).whenPressed(new TiltClimber(climber, ClimberPositions.VERTICAL));

    //new Button(manipulatorController::get)
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

  private static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private static double modifyAxis(double value) {
    // Deadband
    value = deadband(value, 0.1);

    // Square the axis
    value = Math.copySign(value * value, value);

    return value;
  }

}
