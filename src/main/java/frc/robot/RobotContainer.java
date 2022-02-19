// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import javax.lang.model.util.ElementScanner6;

import com.swervedrivespecialties.swervelib.ctre.Falcon500SteerConfiguration;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.AllianceLEDs;
import frc.robot.commands.AutomaticConveyor;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.LEDCommands;
import frc.robot.commands.LimelightAutoTurning;
import frc.robot.subsystems.LED;
import frc.robot.commands.LimelightDefaultCommand;
import frc.robot.commands.PixyCamAutoTurning;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.RunClimber;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.commands.TiltClimber;
import frc.robot.commands.ExtendIntake;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ConveyorSubsystem;
//import frc.robot.commands.PixyCamAutoTurning;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Trajectories;
import frc.robot.subsystems.Climber.ClimberPositions;
import frc.robot.subsystems.Intake.Position;
import frc.robot.subsystems.LED.Pattern;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import frc.robot.subsystems.PixyCamSPI;

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
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  private final Limelight m_limelight = new Limelight();

  private final XboxController m_driverController = new XboxController(0);
  private final XboxController m_manipulatorController = new XboxController(1);
  private final PixyCamSPI m_pixy = new PixyCamSPI(0);
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
  private final Climber m_climber = new Climber();
  private final LED m_ledcommands = new LED();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    ShuffleboardTab xbox = Shuffleboard.getTab("Xbox");
    xbox.addNumber("Left X", () -> {
      return m_driverController.getLeftX();
    }).withWidget(BuiltInWidgets.kGraph);

    xbox.addNumber("Left Y", () -> {
      return m_driverController.getLeftY();
    }).withWidget(BuiltInWidgets.kGraph);

    xbox.addNumber("Right X", () -> {
      return m_driverController.getRightX();
    }).withWidget(BuiltInWidgets.kGraph);
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
        m_drivetrainSubsystem,
        () -> -modifyAxis(m_driverController.getLeftY()),
        () -> -modifyAxis(m_driverController.getLeftX()),
        () -> -modifyAxis(m_driverController.getRightX())));
    m_ledcommands.setDefaultCommand(new AllianceLEDs(m_ledcommands));

    m_limelight.setDefaultCommand(new LimelightDefaultCommand(m_limelight));
    
    conveyorSubsystem.setDefaultCommand(new AutomaticConveyor(conveyorSubsystem,
        () -> {
          if (m_manipulatorController.getPOV() == 0) {
            return Constants.CONVEYOR_POWER;
          } else if (m_manipulatorController.getPOV() == 180) {
            return -Constants.CONVEYOR_POWER;
          } else {
            return 0;
          }
        },
        () -> m_manipulatorController.getBackButton(),
        () -> m_manipulatorController.getStartButton()));

    // Configure the button bindings
    configureButtonBindings();
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
    new Button(m_driverController::getBackButton)
        // No requirements because we don't need to interrupt anything
        .whenPressed(() -> m_drivetrainSubsystem.zeroGyroscope());

    // run intake buttons
    new Button(m_manipulatorController::getAButton)
        .whenPressed(new ExtendIntake(intake));

    new Button(m_manipulatorController::getBButton)
        .whenPressed(new RetractIntake(intake));

    new Button(() -> m_manipulatorController.getRightTriggerAxis() > 0.4)
        .whenPressed(new RunShooter(shooter));

    new Button(() -> m_manipulatorController.getLeftTriggerAxis() > 0.4)
        .whenPressed(new RunIntake(intake));

    new Button(m_driverController::getLeftBumper)
        .whenPressed(() -> {
          if (m_drivetrainSubsystem.getSpeedModifier() == 1.0)
            m_drivetrainSubsystem.setSpeedModifier(0.5);
          else
            m_drivetrainSubsystem.setSpeedModifier(1.0);
        });

    new Button(m_driverController::getYButton)
        .whileHeld(
            new LimelightAutoTurning(
                (output) -> {
                  m_drivetrainSubsystem.drive(
                      -modifyAxis(m_driverController.getLeftY()),
                      -modifyAxis(m_driverController.getLeftX()),
                      output,
                      true);
                },
                m_limelight, m_drivetrainSubsystem));

    new Button(m_driverController::getAButton).whileHeld(new PixyCamAutoTurning(
        (output) -> {
          m_drivetrainSubsystem.drive(
              -modifyAxis(m_driverController.getLeftY()),
              -modifyAxis(m_driverController.getLeftX()),
              output,
              true);
        },
        m_pixy, Pixy2CCC.CCC_SIG1, m_drivetrainSubsystem));

    new Button(m_driverController::getBButton).whileHeld(new PixyCamAutoTurning(
        (output) -> {
          m_drivetrainSubsystem.drive(
              -modifyAxis(m_driverController.getLeftY()),
              -modifyAxis(m_driverController.getLeftX()),
              output,
              true);
        },
        m_pixy, Pixy2CCC.CCC_SIG2, m_drivetrainSubsystem));

    new Button(m_driverController::getAButton).whileHeld(new PixyCamAutoTurning(
        (output) -> {
          double forward = -0.2;
          if (m_pixy.getSeesTarget() != true) {
            forward = 0;
            output = 0.2;
          }
          m_drivetrainSubsystem.drive(
              forward,
              0,
              output,
              false);
        },
        m_pixy, Pixy2CCC.CCC_SIG1, m_drivetrainSubsystem));

    new Button(m_manipulatorController::getLeftBumper).whileHeld(new RunClimber(m_climber, false));

    new Button(m_manipulatorController::getRightBumper).whileHeld(new RunClimber(m_climber, true));

    new Button(m_manipulatorController::getXButton).whenPressed(new TiltClimber(m_climber, ClimberPositions.TILTED));

    new Button(m_manipulatorController::getYButton).whenPressed(new TiltClimber(m_climber, ClimberPositions.VERTICAL));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Create config for trajectory
    TrajectoryConfig config = new TrajectoryConfig(
        DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
        Constants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DrivetrainSubsystem.m_kinematics);

    // An example trajectory to follow. All units in meters.
    Trajectory exampleTrajectory = Trajectories.testTrajectory;
    Transform2d transform = new Transform2d(exampleTrajectory.getInitialPose(), new Pose2d());
    exampleTrajectory = exampleTrajectory.transformBy(transform);
    // TrajectoryGenerator.generateTrajectory(
    // // Start at the origin facing the +X direction
    // new Pose2d(0, 0, new Rotation2d(0)),
    // // Pass through these two interior waypoints, making an 's' curve path
    // List.of(new Translation2d(0, -3), new Translation2d(3, -3), new
    // Translation2d(3, 0) ),
    // // End 3 meters straight ahead of where we started, facing forward
    // new Pose2d(0, 0, new Rotation2d(0)),
    // config);

    var thetaController = new ProfiledPIDController(
        Constants.kPThetaController, 0, 0, Constants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        exampleTrajectory,
        m_drivetrainSubsystem::getPose, // Functional interface to feed supplier
        DrivetrainSubsystem.m_kinematics,

        // Position controllers
        new PIDController(Constants.kPXController, 0, 0),
        new PIDController(Constants.kPYController, 0, 0),
        thetaController,
        m_drivetrainSubsystem::setModuleStates,
        m_drivetrainSubsystem);

    // Reset odometry to the starting pose of the trajectory.
    m_drivetrainSubsystem.resetOdometry(exampleTrajectory.getInitialPose());
    // Run path following command, then stop at the end.
    return swerveControllerCommand.andThen(() -> m_drivetrainSubsystem.drive(0, 0, 0, false));

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
