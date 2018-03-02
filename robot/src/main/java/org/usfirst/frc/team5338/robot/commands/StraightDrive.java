package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StraightDrive extends Command
{
	double distance, targetRotationsLeft, targetRotationsRight, numRotations;
	final double timeConstant = 1;
	private boolean reseting = false;

	public StraightDrive(final double input) // Going 13 inches more.
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.distance = input / (6.0 * Math.PI);
		// Backup plan if need
		// this.setTimeout(this.distance / this.timeConstant);
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		this.targetRotationsLeft = (this.distance);
		this.targetRotationsRight = (this.distance);
	}
	@Override
	protected void execute()
	{
		if(!this.reseting)
		{
			Robot.sensors.resetSensors();
			this.reseting = true;
		}
		final double[] distanceTravelled = Robot.sensors.distances();
		System.out.print(this.targetRotationsLeft);
		System.out.print(" ");
		System.out.println(this.targetRotationsRight);
		// // System.out.println(Arrays.toString(distanceTravelled));
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		Robot.drivetrain.tankDrive(0.60, 0.60);
	}
	@Override
	protected boolean isFinished()
	{
		return (this.targetRotationsLeft < 0) && (this.targetRotationsRight < 0);
		// return this.isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
