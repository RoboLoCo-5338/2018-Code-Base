package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDriveWithJoysticks extends Command
{
	public TankDriveWithJoysticks()
	{
		this.requires(Robot.drivetrain);
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
	@Override
	protected void execute()
	{
		Robot.drivetrain.drive(Robot.oi);
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}