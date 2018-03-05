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
		this.requires(Robot.visionSystem);
	}
	@Override
	protected void execute()
	{
		Robot.visionSystem.track();
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}