//Package for all of our code.
package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.DetectCubes;

//Import all needed classes from our code.
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSystem extends Subsystem
{
	private final NetworkTableInstance instance = NetworkTableInstance.create();
	private final NetworkTable table;
	
	public VisionSystem()
	{
		super();
		this.instance.startServer("/tmp/networktables.persist", "0.0.0.0", 5800);
		this.table = this.instance.getTable("vision");
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new DetectCubes());
	}
	public void track()
	{
		final double x = this.table.getEntry("XCoordinate").getDouble(-1.0);
		final double y = this.table.getEntry("YCoordinate").getDouble(-1.0);
		SmartDashboard.putBoolean("Jetson Connected", this.instance.isConnected());
		SmartDashboard.putBoolean("Cube Found", this.table.getEntry("CubeDetected").getBoolean(false));
		SmartDashboard.putNumber("X Coordinate", x);
		SmartDashboard.putNumber("Y Coordinate", y);
		SmartDashboard.putNumber("Width", this.table.getEntry("Width").getDouble(0.0));
		SmartDashboard.putNumber("Height", this.table.getEntry("Height").getDouble(0.0));
		double angle = 0;
		if(x != 0)
		{
			angle = Math.toDegrees(Math.atan(y / x));
			if(angle > 0)
			{
				angle = 90 - angle;
			}
			else
			{
				angle = 90 + angle;
			}
		}
		SmartDashboard.putNumber("Heading to Face Cube", angle);
	}
}
