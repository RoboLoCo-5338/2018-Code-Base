package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class JoystickControl extends Command
{
	public JoystickControl()
	{
		this.requires(Robot.drivetrain);
	}
	@Override
	protected void end()
	{
		; // Never end control
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