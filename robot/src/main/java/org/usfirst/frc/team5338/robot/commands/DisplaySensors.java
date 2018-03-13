//Package for all of our code.
package org.usfirst.frc.team5338.robot.commands;

//Import all needed classes from our code.
import org.usfirst.frc.team5338.robot.Robot;

//Import all needed classes from WPILib.
import edu.wpi.first.wpilibj.command.Command;

public class DisplaySensors extends Command
{
	public DisplaySensors()
	{
		this.requires(Robot.sensors);
	}
	@Override
	protected void execute()
	{
		Robot.sensors.displayVoltage();
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