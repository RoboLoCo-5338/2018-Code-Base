//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import of necessary subsystem.
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;

//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team5338.robot.subsystems.Sensors;

//Main class that is called by the FMS.
public class Robot extends IterativeRobot
{
	// Creates static subsystem and OI objects for use elsewhere.
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();
	public static final Sensors encoders = new Sensors();

	//Initializes the Navx
	public static final AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) (200));

	//Creates command for Auto
	public static Command Auto;
	
	// Public method that runs once at the beginning of autonomous.

	public void robotInit()
	{
		LiveWindow.disableAllTelemetry();
		// Eventual code to grab field status from FMS will occur here.
		//calibrating ARHS
		while (ahrs.isCalibrating() || ahrs.isMagnetometerCalibrated()) {
		}
	}

	@Override
	public void autonomousInit()
	{
		// Eventual autonomous choosing will occur here.
		final Command Auto = new Autonomous();
		Auto.start();
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	// Public method that runs once on robot startup.

	// Public method that runs once at the beginning of teleop.
	@Override
	public void teleopInit()
	{
		// Eventual code to disable autonomous will occur here.
		try {
			Auto.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Public method that runs continuously every 20ms during autonomous.
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
}