package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetSensors extends Command
{
	public ResetSensors()
	{
		super();
		this.requires(Robot.sensors);
		this.setTimeout(0.25);
	}
	@Override
	protected void execute()
	{
		Robot.sensors.resetSensors();
	}
	@Override
	protected boolean isFinished()
	{
		return this.isTimedOut();
	}
	@Override
	protected void end()
	{
		// Do nothing
	}
}