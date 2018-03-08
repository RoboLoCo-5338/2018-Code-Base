package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClawControl extends Command
{
	public ClawControl()
	{
		this.requires(Robot.claw);
	}
	@Override
	protected void end()
	{
		; // Never ends control
	}
	@Override
	protected void execute()
	{
		Robot.claw.control(Robot.oi);
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}
