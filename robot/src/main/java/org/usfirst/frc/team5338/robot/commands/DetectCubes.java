//Package for all of our code.
package org.usfirst.frc.team5338.robot.commands;

//Import all needed classes from our code.
import org.usfirst.frc.team5338.robot.Robot;

//Import all needed classes from WPILib.
import edu.wpi.first.wpilibj.command.Command;

public class DetectCubes extends Command
{
	public DetectCubes()
	{
		this.requires(Robot.visionsystem);
	}
	@Override
	protected void execute()
	{
		Robot.visionsystem.track();
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
	@Override
	protected void end()
	{
		; // Never ends tracking
	}
}