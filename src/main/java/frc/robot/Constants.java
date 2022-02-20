// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    /**
     * The left-to-right distance between the drivetrain wheels
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = Units.inchesToMeters(9.25)*2; // FIXME Measure and set trackwidth
    /**
     * The front-to-back distance between the drivetrain wheels.
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_WHEELBASE_METERS = Units.inchesToMeters(9.25)*2; // FIXME Measure and set wheelbase

    public static final int DRIVETRAIN_PIGEON_ID = 0; // FIXME Set Pigeon ID

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 17; // FIXME Set front left module drive motor ID
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 18; // FIXME Set front left module steer motor ID
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 5; // *FIXME Set front left steer encoder ID
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(45.08514404296875); // FIXME Measure and set front left steer offset 70.224609

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 14; // FIXME Set front right drive motor ID
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 13; // FIXME Set front right steer motor ID
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 3; // FIXME Set front right steer encoder ID
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(161.97967529296875); // FIXME Measure and set front right steer offset 6.943359

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 11; // FIXME Set back left drive motor ID
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 12; // FIXME Set back left steer motor ID
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 2; // *FIXME Set back left steer encoder ID
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(289.94293212890625); // FIXME Measure and set back left steer offset 289.863281

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 15; // FIXME Set back right drive motor ID
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 16; // FIXME Set back right steer motor ID
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 4; // FIXME Set back right steer encoder ID
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(98.0804443359375); // FIXME Measure and set back right steer offset 225.175781
   
    public static final double kMaxAccelerationMetersPerSecondSquared = 1;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    public static final double kPXController = 0.25;
    public static final double kPYController = 0.25;

    public static final double kPThetaController = 0.25;

    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    public static final int BlinkinPWMPort = 7;
    public static final String LIMELIGHT_TABLE_NAME = "limelight";


    public static final int Intake_Talon_Left = 21; //change these values to match correct Talon ID number
    // public static final int Intake_Talon_Right = 0;
    public static final int LEFT_INTAKE_SOLENOID_FORWARD = 0;  //NEED SOLENOID ID FOR INTAKE
    public static final int LEFT_INTAKE_SOLENOID_REVERSE = 1;
    public static final int RIGHT_INTAKE_SOLENOID_FORWARD = 2;
    public static final int RIGHT_INTAKE_SOLENOID_REVERSE = 3;
    public static final double INTAKE_POWER = 0.2; //need intake power value

    public static final int SHOOTER_TALON_LEFT = 20; //NEED SHOOTER TALON IDS
    // public static final int SHOOTER_TALON_RIGHT = 6;
    public static final int SHOOTER_SOLENOID_FORWARD = 4;//NEED SOLENOID ID FOR THE SHOOTER 
    public static final int SHOOTER_SOLENOID_REVERSE = 5;
    public static final TalonFXInvertType LEFT_FALCON_DIRECTION = TalonFXInvertType.CounterClockwise;
    // public static final TalonFXInvertType RIGH_FALCON_DIRECTION = TalonFXInvertType.CounterClockwise;
    public static final double SHOOTER_POWER = 0.6; //need shooter power value

    public static final int CLIMBER_RIGHT_FALCON = 22;
    public static final int CLIMBER_LEFT_FALCON = 10; //NEED CLIMBER FALCON IDS
    public static final NeutralMode CLIMBER_IDLE_BEHAVIOR = NeutralMode.Brake;
    public static final int CLIMBER_SOLENOID_LEFT_FORWARD = 4;
    public static final int CLIMBER_SOLENOID_LEFT_REVERSE = 5;
    public static final int CLIMBER_SOLENOID_RIGHT_FORWARD = 6;
    public static final int CLIMBER_SOLENOID_RIGHT_REVERSE = 7;
    public static final double CLIMBER_POWER = 0.25; //need climber power value

    public static final double CONVEYOR_POWER = 0.3;


    public static final int CONVEYOR_FALCON = 23;
}
