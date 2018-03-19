package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeClawPosition extends Command
{
	private final int newPosition;
	
	public ChangeClawPosition(final int position)
	{
		super();
		this.requires(Robot.claw);
		this.setTimeout(0.75 * Math.abs(position - Robot.claw.getDartPosition()));
		this.newPosition = position;
	}
	@Override
	protected void execute()
	{
		Robot.claw.setDartPosition(this.newPosition);
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