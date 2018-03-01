//Package for all of our code.
package org.usfirst.frc.team5338.robot.subsystems;

//Import all needed classes from our code.
import org.usfirst.frc.team5338.robot.commands.DetectCubes;

//Import all needed classes from WPILib.
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
		SmartDashboard.putBoolean("Jetson Connected", this.instance.isConnected());
		SmartDashboard.putBoolean("Cube Found", this.table.getEntry("CubeDetected").getBoolean(false));
		SmartDashboard.putNumber("X Coordinate", this.table.getEntry("XCoordinate").getDouble(-1.0));
		SmartDashboard.putNumber("Y Coordinate", this.table.getEntry("YCoordinate").getDouble(-1.0));
		SmartDashboard.putNumber("Width", this.table.getEntry("Width").getDouble(0.0));
		SmartDashboard.putNumber("Height", this.table.getEntry("Height").getDouble(0.0));
	}
}