package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GetSensorData extends Command
{
	public GetSensorData()
	{
		this.requires(Robot.sensors);
	}
	@Override
	protected void end()
	{
		return;
	}
	@Override
	protected void execute()
	{
		Robot.sensors.returnRotation();
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}
