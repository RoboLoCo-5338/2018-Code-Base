//Package for all of our code.
package org.usfirst.frc.team5338.robot;

import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static subsystem and OI objects for use elsewhere.
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();
	// NetworkTableInstance instance = NetworkTableInstance.getDefault();
	NetworkTableInstance instance = NetworkTableInstance.create();
	NetworkTable table;

	// Public method that runs once at the beginning of autonomous.
	@Override
	public void autonomousInit()
	{
		// Eventual autonomous choosing will occur here.
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	// Public method that runs once on robot startup.
	@Override
	public void robotInit()
	{
		this.instance.startServer("/tmp/networktables.persist", "0.0.0.0", 5800);
		this.table = this.instance.getTable("vision");
		// Eventual code to grab field status from FMS will occur here.
	}
	// Public method that runs once at the beginning of teleop.
	@Override
	public void teleopInit()
	{
		// Eventual code to disable autonomous will occur here.
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void teleopPeriodic()
	{
		SmartDashboard.putBoolean("Is Connected", this.instance.isConnected());
		SmartDashboard.putNumber("X Cord", this.table.getEntry("x").getDouble(-22.0));
		SmartDashboard.putBoolean("X Cord Exists", this.table.getEntry("x").exists());
		SmartDashboard.putString("Table Keys", this.table.getKeys().toString());
		// Scheduler.getInstance().run();
	}
}