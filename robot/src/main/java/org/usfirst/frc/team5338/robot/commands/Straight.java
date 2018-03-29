package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Straight extends Command
{
	double rotations, targetRotationsLeft, targetRotationsRight, numRotations, correctionFactor;

	public Straight(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		if(input < 0)
		{
			this.correctionFactor = -13;
		}
		else
		{
			this.correctionFactor = 13;
		}
		this.rotations = (input + this.correctionFactor) / (6.0 * Math.PI);
		this.setTimeout((6.0 * Math.abs(input)) / 138.0);
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		this.targetRotationsLeft = (this.rotations);
		this.targetRotationsRight = (this.rotations);
	}
	@Override
	protected void execute()
	{
		final double[] distanceTravelled = Robot.sensors.distances();
		if(this.rotations > 0)
		{
			this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
			this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
			Robot.drivetrain.drive(0.50, 0.50);
		}
		else
		{
			this.targetRotationsLeft += Math.abs(distanceTravelled[0]);
			this.targetRotationsRight += Math.abs(distanceTravelled[1]);
			Robot.drivetrain.drive(-0.50, -0.50);
		}
	}
	@Override
	protected boolean isFinished()
	{
		// TODO CHECK DISTANCE AND DIRECTION ARE CORRECT AND TIMEOUT IS ENOUGH
		return ((Math.abs(this.targetRotationsLeft) < 0) && (Math.abs(this.targetRotationsRight) < 0))
						|| this.isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
