package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TiltClaw extends Command
{
	public TiltClaw()
	{
		//this.requires(Robot.clawtilter);
	}
	@Override
	protected void end()
	{
	}
	@Override
	protected void execute()
	{
		//Robot.clawtilter.tilt(Robot.oi);
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}
