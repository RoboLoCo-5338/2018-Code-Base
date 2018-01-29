//Package for all of our code.
package org.usfirst.frc.team5338.robot;

import org.usfirst.frc.team5338.robot.subsystems.ClawTilter;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;

//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
//Import of necessary subsystem.
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static subsystem and OI objects for use elsewhere.
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final ClawTilter clawtilter = new ClawTilter();
	public static final OI oi = new OI();
	
	// Public method that runs once at the beginning of autonomous.
	@Override
	public void autonomousInit()
	{
		// Eventual autonomous choosing will occur here.
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	// Public method that runs once on robot startup.
	@Override
	public void robotInit()
	{
		LiveWindow.disableAllTelemetry();
		// Eventual code to grab field status from FMS will occur here.
	}
	// Public method that runs once at the beginning of teleop.
	@Override
	public void teleopInit()
	{
		// Eventual code to disable autonomous will occur here.
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
}