package org.usfirst.frc.team5338.robot;

import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot
{
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();

	@Override
	public void autonomousInit()
	{
	}
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	@Override
	public void robotInit()
	{
	}
	@Override
	public void teleopInit()
	{
	}
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
}