package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command
{
	double angle, targetRotationsLeft, targetRotationsRight, numRotations;
	double[] beginningEncoderData;

	public Turn(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		// this.requires(Robot.sensors);
		this.angle = input;
		this.setTimeout(input);
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
		final double[] distanceTravelled = new double[] {1.0, 1.0}; // Robot.sensors.distances();
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		double right, left;
		if(this.targetRotationsRight > 0)
		{
			right = 0.165;
		}
		else
		{
			right = 0;
		}
		if(this.targetRotationsLeft > 0)
		{
			left = 0.15;
		}
		else
		{
			left = 0;
		}
		Robot.drivetrain.tankDrive(left, right);
	}
	@Override
	protected boolean isFinished()
	{
		return this.isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
