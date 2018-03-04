package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StraightDrive extends Command
{
	double rotations, targetRotationsLeft, targetRotationsRight, numRotations;

	public StraightDrive(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.rotations = (input - 13) / (6.0 * Math.PI);
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
		// System.out.print(this.targetRotationsLeft);
		// System.out.print(" ");
		// System.out.println(this.targetRotationsRight);
		// // System.out.println(Arrays.toString(distanceTravelled));
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		Robot.drivetrain.tankDrive(0.50, 0.50);
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
