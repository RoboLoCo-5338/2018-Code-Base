package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StraightDrive extends Command
{
	double distance, targetRotationsLeft, targetRotationsRight, numRotations;
	// final double timeConstant = 0.0

	public StraightDrive(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.distance = input / (6.0 * Math.PI);
		// Backup plan if need
		// setTimeout(distance / timeConstant)
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		this.targetRotationsLeft = (this.distance);
		this.targetRotationsRight = (this.distance);
		Robot.sensors.resetSensors();
	}
	@Override
	protected void execute()
	{
		final double[] distanceTravelled = Robot.sensors.distances();
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		Robot.drivetrain.drive(0.10, 0);
	}
	@Override
	protected boolean isFinished()
	{
		return (this.targetRotationsLeft > 0) && (this.targetRotationsRight > 0);
		// return isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
