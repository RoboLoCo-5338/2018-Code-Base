package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveForward extends Command
{
	double distance, targetRotationsLeft, targetRotationsRight, numRotations;
	double[] beginningEncoderData;

	public MoveForward(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.distance = input / (6.0 * Math.PI);
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		this.targetRotationsLeft = (this.distance);
		this.targetRotationsRight = (this.distance);
		Robot.sensors.zeroEncoders();
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
		return false;
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
