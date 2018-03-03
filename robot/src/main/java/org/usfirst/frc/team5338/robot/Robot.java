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

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static DriveTrain, VisionSystem, OI, Sensors, and Claw objects for
	// use
	// elsewhere.
	public static final VisionSystem visionSystem = new VisionSystem();
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final Claw claw = new Claw();
	public static final Climber climber = new Climber();
	public static final OI oi = new OI();
	public static final Sensors sensors = new Sensors();
	// Instantiates command for Auto
	public static Command Auto;
	
	// Public method that runs once on robot startup.
	@Override
	public void robotInit()
	{
		// Nothing happens here
	}
	// Public method that runs once at the beginning of autonomous.
	@Override
	public void autonomousInit()
	{
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
		// Eventual code to disable autonomous will occur here.
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
}