package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeGear extends Command
{
	private final boolean shiftState;
	
	public ChangeGear(final boolean state)
	{
		super();
		this.requires(Robot.drivetrain);
		this.setTimeout(0.25);
		this.shiftState = state;
	}
	@Override
	protected void execute()
	{
		Robot.drivetrain.shift(this.shiftState);
	}
	@Override
	protected boolean isFinished()
	{
		return this.isTimedOut();
	}
	@Override
	protected void end()
	{
		; // Do Nothing
	}
}