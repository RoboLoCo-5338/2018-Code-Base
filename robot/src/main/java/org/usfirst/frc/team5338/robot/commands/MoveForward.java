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
		Robot.sensors.resetSensors();
	}
	@Override
	protected void execute()
	{
		Robot.drivetrain.tankDrive(0.50, 0.50);
		// final double[] distanceTravelled = Robot.sensors.distances();
		// System.out.println(Arrays.toString(distanceTravelled));
		// this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		// this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		// if((this.targetRotationsRight > 0) && (this.targetRotationsLeft > 0))
		// {
		// Robot.drivetrain.tankDrive(0.15, 0.165);
		// }
	}
	@Override
	protected boolean isFinished()
	{
		return false; // (this.targetRotationsLeft > 0) && (this.targetRotationsRight > 0);
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
