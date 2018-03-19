package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DepositCube extends Command
{
	public double clawSpeed;

	public DepositCube(final double speed)
	{
		super();
		this.requires(Robot.claw);
		this.clawSpeed = speed;
		this.setTimeout(1);
	}
	@Override
	protected void execute()
	{
		Robot.claw.setWheelSpeed(this.clawSpeed);
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