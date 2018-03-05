package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCube extends Command
{
	public EjectCube()
	{
		super();
		this.requires(Robot.claw);
		this.setTimeout(1.5);
	}
	@Override
	protected void execute()
	{
		Robot.claw.setWheelSpeed(0.40);
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