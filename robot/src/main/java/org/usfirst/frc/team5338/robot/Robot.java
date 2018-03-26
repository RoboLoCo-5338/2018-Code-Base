//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import of necessary subsystem.
import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.Claw;
import org.usfirst.frc.team5338.robot.subsystems.Climber;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5338.robot.subsystems.Sensors;
import org.usfirst.frc.team5338.robot.subsystems.VisionSystem;

//Import of all essential WPILib classes.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static DriveTrain, VisionSystem, OI, Sensors, and Claw objects for
	// use elsewhere.
	public static final VisionSystem visionsystem = new VisionSystem();
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final Claw claw = null;//= new Claw();
	public static final Climber climber = new Climber();
	public static final OI oi = new OI();
	public static final Sensors sensors = new Sensors();
	// Creates SendableChooser objects for use elsewhere.
	public static Command Auto;
	@SuppressWarnings("unused")
	public static SendableChooser<String> autonomousChooser = new SendableChooser<String>();
	
	// Public method that runs once on robot startup.
	@Override
	public void robotInit()
	{
		// Clears all commands
		Scheduler.getInstance().removeAll();
		// Configures SendableChooser to select Autonomous
		Robot.setupAutonomous();
	}
	// Public method that runs once at the beginning of autonomous.
	@Override
	public void autonomousInit()
	{
		// Clears all commands
		Scheduler.getInstance().removeAll();
		// Creates the autonomous with selection
		Robot.Auto = new Autonomous();
		Robot.Auto.start();
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	// Public method that runs once at the beginning of teleop.
	@Override
	public void teleopInit()
	{
		// Clears all commands
		Scheduler.getInstance().removeAll();
		try
		{
			Robot.Auto.cancel();
		}
		catch(final Exception e)
		{
			// No auto was enabled
		}
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
	private static void setupAutonomous()
	{
    Robot.autonomousChooser.addDefault("Center Autonomous (DEFAULT)", "CENTER");
		Robot.autonomousChooser.addObject("Left Autonomous (Scale Priority)", "LEFTSCALESWITCH");
		Robot.autonomousChooser.addObject("Left Autonomous (Switch Priority)", "LEFTSWITCHSCALE");
		Robot.autonomousChooser.addObject("Right Autonomous (Scale Priority)", "RIGHTSCALESWITCH");
		Robot.autonomousChooser.addObject("Right Autonomous (Switch Priority)", "RIGHTSWITCHSCALE");
		Robot.autonomousChooser.addObject("Baseline Cross Autonomous", "BASELINE");
		Robot.autonomousChooser.addObject("NO AUTONOMOUS (DANGER)", "NOTHING");
		SmartDashboard.putData("Autonomous Choice", Robot.autonomousChooser);
	}
}