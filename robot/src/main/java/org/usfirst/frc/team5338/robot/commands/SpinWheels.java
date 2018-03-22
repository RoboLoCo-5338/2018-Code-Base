package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinWheels extends Command
{
	private final int wheelSpeed;
	
	public SpinWheels(double wheelSpeed, double timeOut)
	{
		super();
		this.requires(Robot.claw);
		this.setTimeout(timeOut);
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
		Robot.claw.setWheelSpeed(0.0);
	}
}