package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StraightDrive extends Command
{
	double distance, targetRotationsLeft, targetRotationsRight, numRotations;

	public StraightDrive(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.distance = (input + 13) / (6.0 * Math.PI); // Going 13 inches more.
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
		final double[] distanceTravelled = Robot.sensors.distances();
		// System.out.print(this.targetRotationsLeft);
		// System.out.print(" ");
		// System.out.println(this.targetRotationsRight);
		// // System.out.println(Arrays.toString(distanceTravelled));
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		Robot.drivetrain.tankDrive(0.60, 0.60);
	}
	@Override
	protected boolean isFinished()
	{
		return (this.targetRotationsLeft < 0) && (this.targetRotationsRight < 0);
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
