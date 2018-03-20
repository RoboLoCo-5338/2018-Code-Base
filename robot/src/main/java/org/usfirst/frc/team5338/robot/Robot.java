//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import all needed classes from our code.
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;

//Import all needed classes from WPILib.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static DriveTrain, OI, and Dart objects for use elsewhere.
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();
	
	// Public method that runs once on robot startup.
	@Override
	public void robotInit()
	{
		// Clears all commands
		Scheduler.getInstance().removeAll();
	}
	// Public method that runs once at the beginning of autonomous.
	@Override
	public void autonomousInit()
	{
		// Clears all commands
		Scheduler.getInstance().removeAll();
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
	}
	// Public method that runs continuously every 20ms during teleop.
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
}