package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Delay extends Command
{
	public Delay(final double delay)
	{
		super();
		this.setTimeout(delay);
	}
	@Override
	protected void execute()
	{
		; // Does nothing
	}
	@Override
	protected boolean isFinished()
	{
		// TODO CHECK FUNCTIONALITY
		return this.isTimedOut();
	}
	@Override
	protected void end()
	{
		; // Does nothing
	}
}