package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Straight extends Command
{
	double rotations, targetRotationsLeft, targetRotationsRight, numRotations;

	public Straight(final double input)
	{
		// Input in inches to travel
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.rotations = (input - 13) / (6.0 * Math.PI);
		this.setTimeout((6.0 * input) / 138.0);
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
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		Robot.drivetrain.drive(0.50, 0.50);
		SmartDashboard.putNumberArray("Distances travelled", distanceTravelled);
	}
	@Override
	protected boolean isFinished()
	{
		return ((this.targetRotationsLeft < 0) && (this.targetRotationsRight < 0)) || this.isTimedOut();
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
