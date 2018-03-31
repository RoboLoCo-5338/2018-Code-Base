package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetClawSpeed extends Command
{
	private final double newWheelSpeed;
	
	public SetClawSpeed(final double wheelSpeed)
	{
		super();
		this.requires(Robot.claw);
		this.setTimeout(0.25);
		this.newWheelSpeed = wheelSpeed;
	}
	@Override
	protected void execute()
	{
		Robot.claw.setWheelSpeed(this.newWheelSpeed);
	}
	@Override
	protected boolean isFinished()
	{
		return this.isTimedOut();
	}
	@Override
	protected void end()
	{
		;
	}
}