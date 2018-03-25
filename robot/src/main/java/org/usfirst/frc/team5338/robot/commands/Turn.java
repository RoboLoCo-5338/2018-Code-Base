package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command
{
	double angle, initalHeading, targetHeading;
	
	public Turn(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.angle = input;
		this.initalHeading = Robot.sensors.ahrs.getYaw();
		this.targetHeading = this.initalHeading + this.angle;
		this.setTimeout((1.5 * Math.abs(this.angle)) / 90.0);
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		Robot.sensors.resetEncoders();
	}
	@Override
	protected void execute()
	{
		if(this.angle > Robot.sensors.ahrs.getYaw())
		{
			Robot.drivetrain.drive(0.70, -0.70);
		}
		else
		{
			Robot.drivetrain.drive(-0.70, 0.70);
		}
	}
	@Override
	protected boolean isFinished()
	{
		return (Math.abs(Robot.sensors.ahrs.getYaw() - this.targetHeading) < 2) || this.isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}