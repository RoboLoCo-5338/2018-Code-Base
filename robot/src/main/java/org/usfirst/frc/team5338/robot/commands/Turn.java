package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command
{
	double angle, initalHeading, targetHeading;
	// final double timeConstant = 0.0

	public Turn(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.angle = input;
		this.initalHeading = Robot.sensors.ahrs.getYaw();
		this.targetHeading = this.initalHeading + this.angle;
		// Backup plan if need
		// setTimeout(angle / timeConstant)
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		Robot.sensors.resetSensors();
	}
	@Override
	protected void execute()
	{
		if(this.angle > 0)
		{
			Robot.drivetrain.drive(0.0, 0.10);
		}
		else
		{
			Robot.drivetrain.drive(0.0, -0.10);
		}
	}
	@Override
	protected boolean isFinished()
	{
		return Math.abs(Robot.sensors.ahrs.getYaw() - this.targetHeading) < 1.5;
		// return isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}