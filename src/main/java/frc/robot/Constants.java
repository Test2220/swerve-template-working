// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

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
    public static final double DRIVETRAIN_WIDTH_METERS = Units.inchesToMeters(9.25)*2; 
    /**
     * The front-to-back distance between the drivetrain wheels.
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_LENGTH_METERS = Units.inchesToMeters(9.25)*2; 

    // Calculate Ratio
    public static final double DRIVETRAIN_DIAMETER = Math.sqrt((DRIVETRAIN_LENGTH_METERS * DRIVETRAIN_LENGTH_METERS) + (DRIVETRAIN_WIDTH_METERS * DRIVETRAIN_WIDTH_METERS));

    public static final double FRONT_OF_BUMPERS_TO_INTAKE = Units.inchesToMeters(11);
    public static final double robotLengthWithBumpers = Units.inchesToMeters(32);
    // CAN Bus IDs

    // Front Left Drive Motor
    public static final int FL_MODULE_DM = 17;
    // Front Left Steer Motor
    public static final int FL_MODULE_SM = 18;
    // Front Left Steer Encoder
    public static final int FL_MODULE_SE = 5;

    // Front Right Drive Motor
    public static final int FR_MODULE_DM = 14;
    // Front Right Steer Motor
    public static final int FR_MODULE_SM = 13;
    // Front Right Steer Encoder
    public static final int FR_MODULE_SE = 3;

    // Back Left Drive Motor
    public static final int BL_MODULE_DM = 11;
    // Back Left Steer Motor
    public static final int BL_MODULE_SM = 12;
    // Back Left Steer Encoder
    public static final int BL_MODULE_SE = 2;

    // Back Right Drive Motor
    public static final int BR_MODULE_DM = 15;
    // Back Right Steer Motor
    public static final int BR_MODULE_SM = 16;
    // Back Right Steer Encoder
    public static final int BR_MODULE_SE = 4;

    

    // Offsets
    public static final double FL_STEER_OFFSET = -Math.toRadians(251.42211914062497 - 180);
    public static final double FR_STEER_OFFSET = -Math.toRadians(161.97967529296875);
    public static final double BL_STEER_OFFSET = -Math.toRadians(289.94293212890625);
    public static final double BR_STEER_OFFSET = -Math.toRadians(98.0804443359375);


    //Pipeline 
    public static final int INTAKE_LIMELIGHT_BLUE_PIPELINE = 0;
    public static final int INTAKE_LIMELIGHT_RED_PIPELINE = 1;


    public static final int SHOOTER_LIMELIGHT_HUB_PIPELINE = 0;
    // Max
    public static final double MAX_VOLTAGE = 12.0;
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 *
        SdsModuleConfigurations.MK4_L2.getDriveReduction() *
        SdsModuleConfigurations.MK4_L2.getWheelDiameter() * Math.PI;
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
        Math.hypot(DRIVETRAIN_WIDTH_METERS / 2.0, DRIVETRAIN_LENGTH_METERS / 2.0);

    public static final double kMaxAccelerationMetersPerSecondSquared = 1;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    public static final double kPXController = 0.25;
    public static final double kPYController = 0.25;

    public static final double kPThetaController = 0.25;

    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    public static final int BlinkinPWMPort = 7;
    public static final String LIMELIGHT_TABLE_NAME_SHOOTER = "limelight-shooter";
    public static final String LIMELIGHT_TABLE_NAME_INTAKE = "limelight-intake";



    public static final int INTAKE_TALON_LEFT = 21; 
    public static final int LEFT_INTAKE_SOLENOID_FORWARD = 0;  
    public static final int LEFT_INTAKE_SOLENOID_REVERSE = 1;
    public static final int RIGHT_INTAKE_SOLENOID_FORWARD = 2;
    public static final int RIGHT_INTAKE_SOLENOID_REVERSE = 3;
    public static final double INTAKE_POWER = 0.25; 
    public static final double REVERSE_INTAKE_POWER = -0.25;
    
    public static final int INTAKE_THRESHOLD = 5;
    public static final double INTAKE_UNJAM_POWER = 0.8;
    public static final double INTAKE_UNJAM_POWER_MAX = 1;

    public static final int SHOOTER_TALON_LEFT = 20; 
    public static final int SHOOTER_SOLENOID_FORWARD = 4;
    public static final int SHOOTER_SOLENOID_REVERSE = 5;
    public static final TalonFXInvertType LEFT_FALCON_DIRECTION = TalonFXInvertType.Clockwise;
    // public static final TalonFXInvertType RIGH_FALCON_DIRECTION = TalonFXInvertType.CounterClockwise;
    public static final double SHOOTER_POWER_HIGH = -1; //need shooter power value
    public static final double SHOOTER_POWER_LOW = -0.5;
    public static final boolean AUTO_LOW_GOAL = false;

    // public static final ShuffleboardTab SHUFFLEBOARD_SHOOTER = Shuffleboard.getTab("Shooter");
    // public static final NetworkTableEntry SHUFFLEBOARD_SHOOTER_POWER_HIGH = 
    //     SHUFFLEBOARD_SHOOTER
    //         .addPersistent("Shooter Power High", SHOOTER_POWER_HIGH)
    //         .withSize(1, 1)
    //         .withPosition(0, 0)
    //         .getEntry();
    // public static final NetworkTableEntry SHUFFLEBOARD_SHOOTER_POWER_LOW = 
    //     SHUFFLEBOARD_SHOOTER
    //         .addPersistent("Shooter Power Low", SHOOTER_POWER_LOW)
    //         .withSize(1, 1)
    //         .withPosition(0, 1)
    //         .getEntry();

    public static final int CLIMBER_RIGHT_FALCON = 10;
    public static final int CLIMBER_LEFT_FALCON = 22; 
    public static final NeutralMode CLIMBER_IDLE_BEHAVIOR = NeutralMode.Brake;
    public static final int CLIMBER_SOLENOID_LEFT_FORWARD = 4;
    public static final int CLIMBER_SOLENOID_LEFT_REVERSE = 5;
    public static final int CLIMBER_SOLENOID_RIGHT_FORWARD = 6;
    public static final int CLIMBER_SOLENOID_RIGHT_REVERSE = 7;
    public static final double CLIMBER_POWER = 0.75; 

    public static final double CONVEYOR_POWER = 0.3;


    public static final int CONVEYOR_FALCON = 23;

    public static final int PHOTOEYE_SENSOR_INTAKE = 0;

    public static final int PHOTOEYE_SENSOR_LAUNCHER = 2;

    public static final double OFFSET = (-8 / 196.85) + 1;

    public static final double SLEW_RATE_LIMIT = 3;

    public static final int LEFT_CLIMB_LIMIT_BOTTOM_PORT = 3;
    public static final int LEFT_CLIMB_LIMIT_TOP_PORT = 4;
    public static final int RIGHT_CLIMB_LIMIT_BOTTOM_PORT = 5;
    public static final int RIGHT_CLIMB_LIMIT_TOP_PORT = 6;

    public static final boolean CLIMB_LIMITS_INVERTED = false;
}
