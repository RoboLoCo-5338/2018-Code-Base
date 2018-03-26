package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetClawSpeed extends Command
{
	private final double wheelSpeed;

	public SetClawSpeed(final double wheelSpeed)
	{
		super();
		this.requires(Robot.claw);
		this.setTimeout(0.25);
		this.wheelSpeed = wheelSpeed;
	}
	@Override
	protected void execute()
	{
		Robot.claw.setWheelSpeed(this.wheelSpeed);
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