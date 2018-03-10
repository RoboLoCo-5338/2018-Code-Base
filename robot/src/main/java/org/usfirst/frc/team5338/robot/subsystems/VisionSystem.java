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
		// NetworkTableInstance.getDefault().setServer("/CameraPublisher");
		// NetworkTableInstance.getDefault().getEntry("/CameraPublisher/camera/streams")
		// .setStringArray(new String[] {"mjpeg:http://10.53.38.75:5805/index.html"});
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new DetectCubes());
	}
	public void track()
	{
		SmartDashboard.putBoolean("Jetson Connected", this.instance.isConnected());
		final boolean cubePresent = this.table.getEntry("CubeDetected").getBoolean(false);
		SmartDashboard.putBoolean("Cube Found", cubePresent);
		if(cubePresent)
		{
			SmartDashboard.putNumber("Cube Heading",
							-((this.table.getEntry("XCoordinate").getDouble(0.0) + 130) - 320));
		}
		else
		{
			SmartDashboard.putNumber("Cube Heading", 0.0);
		}
	}
}
