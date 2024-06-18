// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.swervedrive.SwerveSubsystem;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem m_exampleSubsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
  private final SendableChooser<Command> autoChooser;
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private static final CommandXboxController m_driverController =
      new CommandXboxController(0);
  private static final Trigger drive = m_driverController.leftBumper();
  private static final Trigger angle = m_driverController.rightBumper();
  private static final Trigger button_A = m_driverController.a();
  private static final Trigger button_b = m_driverController.b();
  private static final Trigger button_x = m_driverController.x();

    //private static final Trigger button_b = m_driverController.b();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
  

    configureBindings();
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    
    m_exampleSubsystem.setDefaultCommand(
      m_exampleSubsystem.driveCommandu(()->MathUtil.applyDeadband(
        -m_driverController.getLeftY(),.05), ()-> MathUtil.applyDeadband(-m_driverController.getLeftX(),.15), 
        ()-> m_driverController.getRightX()));
        
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    button_A.onTrue(m_exampleSubsystem.autoAlign());
    button_A.onFalse(m_exampleSubsystem.driveCommandu(()->MathUtil.applyDeadband(-m_driverController.getLeftY(),.05), ()-> MathUtil.applyDeadband(-m_driverController.getLeftX(),.15), ()-> m_driverController.getRightX()));
    
    button_b.onTrue(m_exampleSubsystem.drive_limelight());
    button_b.onFalse(m_exampleSubsystem.driveCommandu(()->MathUtil.applyDeadband(-m_driverController.getLeftY(),.05), ()-> MathUtil.applyDeadband(-m_driverController.getLeftX(),.15), ()-> m_driverController.getRightX()));

    button_x.whileTrue(m_exampleSubsystem.driveCommandu(()->MathUtil.applyDeadband(-m_driverController.getLeftY(),.05), ()-> MathUtil.applyDeadband(-m_driverController.getLeftX(),.15), ()-> m_exampleSubsystem.rot_follow()));
    button_x.whileFalse(m_exampleSubsystem.driveCommandu(()->MathUtil.applyDeadband(-m_driverController.getLeftY(),.05), ()-> MathUtil.applyDeadband(-m_driverController.getLeftX(),.15), ()-> m_driverController.getRightX()));
    
  
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}