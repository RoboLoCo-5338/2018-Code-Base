//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import of necessary subsystem.
import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5338.robot.subsystems.Sensors;

//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static subsystem and OI objects for use elsewhere.
	public static final DriveTrain drivetrain = new DriveTrain();
	// public static final ClawTilter clawtilter = new ClawTilter();
	public static final OI oi = new OI();
	public static final Sensors sensors = new Sensors();
	// Creates command for Auto
	public static Command Auto;

	// Public method that runs once at the beginning of autonomous.
	@Override
	public void robotInit()
	{
		// LiveWindow.disableAllTelemetry();
		// Eventual code to grab field status from FMS will occur here.
	}
	@Override
	public void autonomousInit()
	{
		Robot.Auto = new Autonomous();
		// Eventual autonomous choosing will occur here.
		Robot.Auto.start();
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	// Public method that runs once on robot startup.
	// Public method that runs once at the beginning of teleop.
	@Override
	public void teleopInit()
	{
		// Eventual code to disable autonomous will occur here.
		try
		{
			Robot.Auto.cancel();
		}
		catch(final Exception e)
		{
		}
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
}