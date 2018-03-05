package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlClimber extends Command
{
	public ControlClimber()
	{
		this.requires(Robot.climber);
	}
	@Override
	protected void end()
	{
		// Never ends control
	}
	@Override
	protected void execute()
	{
		Robot.climber.control(Robot.oi);
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}
