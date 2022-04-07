// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
// import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
// import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.autopaths.ReferenceAOneBall;
import frc.robot.autopaths.ReferenceATwoBall;
import frc.robot.autopaths.ReferenceBOneBall;
import frc.robot.autopaths.ReferenceBTwoBall;
import frc.robot.autopaths.ReferenceCFiveBall;
import frc.robot.autopaths.ReferenceCFourBall;
import frc.robot.autopaths.ReferenceCOneBall;
import frc.robot.autopaths.ReferenceCThreeBall;
import frc.robot.autopaths.ReferenceCTwoBall;
import frc.robot.autopaths.ReferenceDOneBall;
import frc.robot.autopaths.ReferenceDTwoBall;
import frc.robot.commands.AllianceLEDs;
import frc.robot.commands.AutoRampPowerIntake;
import frc.robot.commands.AutomaticConveyor;
import frc.robot.commands.DefaultClimber;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.ExtendIntake;
// import frc.robot.commands.FollowPath;
import frc.robot.commands.LimelightAutoTurning;
import frc.robot.commands.LimelightDefaultCommand;
// import frc.robot.commands.PPFollowPath;
// import frc.robot.commands.PixyCamAutoTurning;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.RunIntakeTeleop;
import frc.robot.commands.RunShooter;
// import frc.robot.commands.TerminalTwoBallAuto;
import frc.robot.commands.TiltClimber;
// import frc.robot.subsystems.BrownOutMonitor;
// import frc.robot.commands.HangarTwoBallAuto;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.ClimberPositions;
import frc.robot.subsystems.Conveyor;
//import frc.robot.commands.PixyCamAutoTurning;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Limelight;
// import frc.robot.subsystems.PixyCamSPI;
import frc.robot.subsystems.Shooter;
// import io.github.pseudoresonance.pixy2api.Pixy2CCC;

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

  private final Limelight shooterLimelight = new Limelight(Constants.LIMELIGHT_TABLE_NAME_SHOOTER);
  private final Limelight intakeLimelight = new Limelight(Constants.LIMELIGHT_TABLE_NAME_INTAKE);
 
  @SuppressWarnings("unused")
  private final DriverTab driverTab = new DriverTab();

  //private final PixyCamSPI pixy = new PixyCamSPI(0);
  // private final PowerDistribution powerDistribution = new PowerDistribution();

  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  
  // @SuppressWarnings("unused") // BrownOutMonitor just runs in the background and doesn't have any methods used here. 
  //                             // it is just assigned to a variable so that it is not garbage collected.
  // private final BrownOutMonitor brownOutMonitor = new BrownOutMonitor(manipulatorController);

  //Slew Rate Limiters
  SlewRateLimiter leftY = new SlewRateLimiter(Constants.SLEW_RATE_LIMIT);
  SlewRateLimiter leftX = new SlewRateLimiter(Constants.SLEW_RATE_LIMIT);
  SlewRateLimiter rightX = new SlewRateLimiter(Constants.SLEW_RATE_LIMIT);

  private final DefaultDriveCommand driveCommand = new DefaultDriveCommand(
    drivetrain,
    () -> -modifyAxis(leftY.calculate(driverController.getLeftY())),
    () -> -modifyAxis(leftX.calculate(-driverController.getLeftX())),
    () -> -modifyAxis(rightX.calculate(-driverController.getRightX())));

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Optimize our loop time to avoid loop overruns
    LiveWindow.disableAllTelemetry();


    // ShuffleboardTab xbox = Shuffleboard.getTab("Xbox");
    // xbox.addNumber("Left X", () -> {
    //   return driverController.getLeftX();
    // }).withWidget(BuiltInWidgets.kGraph);

    // xbox.addNumber("Left Y", () -> {
    //   return driverController.getLeftY();
    // }).withWidget(BuiltInWidgets.kGraph);

    // xbox.addNumber("Right X", () -> {
    //   return driverController.getRightX();
    // }).withWidget(BuiltInWidgets.kGraph);
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    drivetrain.setDefaultCommand(driveCommand);

    led.setDefaultCommand(new AllianceLEDs(led));

    shooterLimelight.setDefaultCommand(new LimelightDefaultCommand(shooterLimelight, 0));
    intakeLimelight.setDefaultCommand(new LimelightDefaultCommand(intakeLimelight, 0));


    conveyor.setDefaultCommand(new AutomaticConveyor(conveyor,
        () -> {
          if (manipulatorController.getPOV() == 0) {
            return Constants.SHUFFLEBOARD_CONVEYOR_SPEED.getDouble(0.4);
          } else if (manipulatorController.getPOV() == 180) {
            return -Constants.SHUFFLEBOARD_CONVEYOR_SPEED.getDouble(0.4);
          } else {
            return 0;
          }
        },
        () -> manipulatorController.getBackButton(),
        () -> manipulatorController.getStartButton()));

    climber.setDefaultCommand(new DefaultClimber(climber, 
        () -> modifyAxis(manipulatorController.getRightY()),
        () -> -modifyAxis(manipulatorController.getLeftY()),
        () -> !manipulatorController.getLeftStickButton(),
        () -> !manipulatorController.getRightStickButton()
    ));

    // Configure the button bindings
    configureButtonBindings();

    autoChooser.setDefaultOption("Do Nothing", new InstantCommand());
    autoChooser.addOption("Reference A One Ball Auto", new ReferenceAOneBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference A Two Ball Auto", new ReferenceATwoBall(intake, drivetrain, shooter, conveyor));
   
    autoChooser.addOption("Reference B One Ball Auto", new ReferenceBOneBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference B tWO Ball Auto", new ReferenceBTwoBall(intake, drivetrain, shooter, conveyor));


    autoChooser.addOption("Reference C One Ball Auto", new ReferenceCOneBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference C Two Ball Auto", new ReferenceCTwoBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference C Three Ball Auto", new ReferenceCThreeBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference C Four Ball Auto", new ReferenceCFourBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference C Five Ball Auto", new ReferenceCFiveBall(intake, drivetrain, shooter, conveyor));

    autoChooser.addOption("Reference D One Ball Auto", new ReferenceDOneBall(intake, drivetrain, shooter, conveyor));
    autoChooser.addOption("Reference D Two Ball Auto", new ReferenceDTwoBall(intake, drivetrain, shooter, conveyor));
    

    Shuffleboard.getTab("Auto").add("Auto", autoChooser);

    //System.out.println(GeomUtil.getRotation(FieldConstants.referenceCRobotCenter.getTranslation(), FieldConstants.cargoG.getTranslation()).getDegrees());
  //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!refC" + FieldConstants.referenceC.getTranslation().toString());
  //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!refCOpposite" + FieldConstants.referenceCOpposite.getTranslation().toString());
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!cargoG" + FieldConstants.cargoG.getTranslation().toString());
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!cargoGCenter" + FieldConstants.cargoGCenter.getTranslation().toString());



//   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CargoDToCargoE" + GeomUtil.poseToGetCargo(
//       FieldConstants.cargoD.getTranslation(), 
//       FieldConstants.cargoE.getTranslation()
//   ).getRotation().getDegrees());

//   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CargoETOrefC" + GeomUtil.poseToGetCargo(
//     FieldConstants.cargoE.getTranslation(), 
//     FieldConstants.referenceCRobotCenter.getTranslation()
// ).getRotation().getDegrees());

// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!refCTOCargoG" + GeomUtil.poseToGetCargo(
//     FieldConstants.referenceCRobotCenter.getTranslation(), 
//     FieldConstants.cargoG.getTranslation()
// ).getRotation().getDegrees());

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

    new Button(() -> driverController.getPOV() == 180)
        .whenPressed(() -> 
            drivetrain.decreaseSpeed()
        );
    new Button(() -> driverController.getPOV() == 0)
        .whenPressed(() ->
            drivetrain.increaseSpeed()
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
                shooterLimelight,Constants.SHOOTER_LIMELIGHT_HUB_PIPELINE, drivetrain));

    new Button(driverController::getAButton)
        .whileHeld(
            new LimelightAutoTurning(
                (output) -> {
                  drivetrain.drive(
                      -modifyAxis(driverController.getLeftY()),
                      -modifyAxis(driverController.getLeftX()),
                      output,
                      true);
                },
                intakeLimelight, Constants.INTAKE_LIMELIGHT_BLUE_PIPELINE, drivetrain));

    new Button(driverController::getBButton)
        .whileHeld(
            new LimelightAutoTurning(
                (output) -> {
                  drivetrain.drive(
                      -modifyAxis(driverController.getLeftY()),
                      -modifyAxis(driverController.getLeftX()),
                      output,
                      true);
                },
                intakeLimelight, Constants.INTAKE_LIMELIGHT_RED_PIPELINE, drivetrain));

    // new Button(driverController::getAButton).whileHeld(new PixyCamAutoTurning(
    //     (output) -> {
    //       drivetrain.drive(
    //           -modifyAxis(driverController.getLeftY()),
    //           -modifyAxis(driverController.getLeftX()),
    //           output,
    //           false);
    //     },
    //     pixy, Pixy2CCC.CCC_SIG1, drivetrain));

    // new Button(driverController::getBButton).whileHeld(new PixyCamAutoTurning(
    //     (output) -> {
    //       drivetrain.drive(
    //           -modifyAxis(driverController.getLeftY()),
    //           -modifyAxis(driverController.getLeftX()),
    //           output,
    //           false);
    //     },
    //     pixy, Pixy2CCC.CCC_SIG2, drivetrain));

    // new Button(driverController::getXButton).whileHeld(new PixyCamAutoTurning(
    //     (output) -> {
    //       double forward = 0.2;
    //       if (pixy.getSeesTarget() != true) {
    //         forward = 0;
    //         output = 0.2;
    //       }
    //       drivetrain.drive(
    //           forward,
    //           0,
    //           output,
    //           true);
    //     },
    //     pixy, Pixy2CCC.CCC_SIG1, drivetrain));
    
   new Button(()-> driverController.getPOV() == 0)
      .whenPressed(() -> {
        manipulatorController.setRumble(RumbleType.kLeftRumble, 1);
        manipulatorController.setRumble(RumbleType.kRightRumble, 1);

      })
      .whenReleased(() -> {
        manipulatorController.setRumble(RumbleType.kLeftRumble, 0);
        manipulatorController.setRumble(RumbleType.kRightRumble, 0);

      });

    new Button(() -> driverController.getLeftTriggerAxis() > 0.4)
      .whileHeld(
          new RunShooter(shooter, conveyor, false))
;
     new Button(driverController::getLeftBumper)      
      .whileHeld(
          new RunShooter(shooter, conveyor, true));

    // new Button(driverController::getAButton).whileHeld(new PixyCamAutoTurning(
    //     (output) -> {
    //       drivetrain.drive(
    //           -modifyAxis(driverController.getLeftY()),
    //           -modifyAxis(driverController.getLeftX()),
    //           output,
    //           false);
    //     },
    //     pixy, Pixy2CCC.CCC_SIG1, drivetrain));

    // new Button(driverController::getBButton).whileHeld(new PixyCamAutoTurning(
    //     (output) -> {
    //       drivetrain.drive(
    //           -modifyAxis(driverController.getLeftY()),
    //           -modifyAxis(driverController.getLeftX()),
    //           output,
    //           false);
    //     },
    //     pixy, Pixy2CCC.CCC_SIG2, drivetrain));

    // new Button(driverController::getXButton).whileHeld(new PixyCamAutoTurning(
    //     (output) -> {
    //       double forward = 0.2;
    //       if (pixy.getSeesTarget() != true) {
    //         forward = 0;
    //         output = 0.2;
    //       }
    //       drivetrain.drive(
    //           forward,
    //           0,
    //           output,
    //           true);
    //     },
    //     pixy, Pixy2CCC.CCC_SIG1, drivetrain));
    
   new Button(()-> driverController.getPOV() == 0).whileHeld(new FunctionalCommand(
     ()->{}, 
     ()->{
       manipulatorController.setRumble(RumbleType.kLeftRumble, 1);
       manipulatorController.setRumble(RumbleType.kRightRumble, 1);

     }, 
     (interrupted)->{
      manipulatorController.setRumble(RumbleType.kLeftRumble, 0);
      manipulatorController.setRumble(RumbleType.kRightRumble, 0);


     }, 
     ()->false));

    // new Button(() -> driverController.getPOV() == 180).whenPressed(new GoToCommand(drivetrain, new Position(0, 0, 0)));

      // new Button(() -> driverController.getLeftTriggerAxis() > 0.4)
      // .whenPressed(
      //   () -> driveCommand.setMode(1)
      // );

      // new Button(() -> driverController.getRightTriggerAxis() > 0.4)
      // .whenPressed(
      //   () -> driveCommand.setMode(0)
      // );

    //run shooter buttons
    // new Button(manipulatorController::getAButton)
    //     .whileHeld(new RunShooterVelocity(shooter, conveyor, false));

    // new Button(manipulatorController::getBButton)
    //     .whileHeld(new RunShooterVelocity(shooter, conveyor, true));

    new Button(() -> manipulatorController.getRightTriggerAxis() > 0.4)
        .whileHeld(new RunShooter(shooter, conveyor, true));

    new Button(() -> manipulatorController.getLeftTriggerAxis() > 0.4)
        //.whileHeld(new RunIntakeTeleop(intake, false));
      .whenPressed(new ExtendIntake(intake))
      .whileHeld(new AutoRampPowerIntake(intake))
      .whenReleased(new RetractIntake(intake));

    new Button(manipulatorController::getLeftBumper)
        .whileHeld(new RunIntakeTeleop(intake, true));

    new Button(manipulatorController::getRightBumper)
      .whileHeld(new RunShooter(shooter, conveyor, false));

    new Button(manipulatorController::getXButton).whenPressed(new TiltClimber(climber, ClimberPositions.TILTED));

    new Button(manipulatorController::getYButton).whenPressed(new TiltClimber(climber, ClimberPositions.VERTICAL));
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
    value = deadband(value, 0.05);

    // Square the axis
    value = Math.copySign(value * value, value);

    return value;
  }

  // public static final NetworkTableEntry SLEW_RATE = 
  //       Shuffleboard.getTab("Drivetrain")
  //           .addPersistent("Slew Rate Limit", Constants.SLEW_RATE_LIMIT)
  //           .withSize(1, 1)
  //           .withPosition(0, 0)
  //           .getEntry();

}
