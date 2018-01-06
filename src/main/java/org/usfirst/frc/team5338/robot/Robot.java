package org.usfirst.frc.team5338.robot;

import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.BallHandler;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5338.robot.subsystems.GearHandler;
import org.usfirst.frc.team5338.robot.subsystems.GearPicker;
import org.usfirst.frc.team5338.robot.subsystems.Wincher;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    public static SendableChooser<String> autonomousChooser;

    public static final OI oi = new OI();
    public static final DriveTrain drivetrain = new DriveTrain();
    public static final BallHandler ballhandler = new BallHandler();
    public static final Wincher wincher = new Wincher();
    public static final GearHandler gearhandler = new GearHandler();
    public static final GearPicker gearpicker = new GearPicker();

    private static Command autonomousCommand;

    public static final AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) (200));

    @Override
    public void robotInit() {
	while (ahrs.isCalibrating() || ahrs.isMagnetometerCalibrated()) {
	}

	/*UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	camera.setResolution(360, 360);
	camera.setFPS(60);
	camera.setExposureHoldCurrent();
	camera.setWhiteBalanceHoldCurrent();
	camera.setBrightness(camera.getBrightness());*/

	SmartDashboard.putString("AUTONOMOUS CHOICE", "CENTERGEAR");
    }

    @Override
    public void autonomousInit() {
	autonomousCommand = new Autonomous();
	autonomousCommand.start();
    }

    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
	try {
	    autonomousCommand.cancel();
	} catch (Exception e) {
	}
    }

    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();
    }
}