package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.JoystickDrive;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem
{
	// Field variables that we will use
	// Talons: motor controllers that we use on the robot
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(2);
	public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(4);
	// Contol-group objects used to move the left drive drive and right drive motors
	// in sync (two drive motors per side)
	private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
	private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
	// Creates a drive object that will define how the left and right motor sets are
	// configured (currently as an arcade drive)
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	// Objects that control the shift and compressor mechanism
	private final Compressor driveCompressor = new Compressor(8);
	private final DoubleSolenoid driveSolenoid = new DoubleSolenoid(8, 3, 4);

	public DriveTrain()
	{
		super();
		this.driveCompressor.setClosedLoopControl(true);
		this.driveCompressor.start();
		this.driveSolenoid.set(DoubleSolenoid.Value.kForward);
		this.configureTalons();
	}
	public void configureTalons()
	{
		this.DRIVEL1.configNeutralDeadband(0.001, 0);
		this.DRIVER1.configNeutralDeadband(0.001, 0);
		this.DRIVEL2.configNeutralDeadband(0.001, 0);
		this.DRIVER2.configNeutralDeadband(0.001, 0);
		this.DRIVEL1.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVEL1.setControlFramePeriod(ControlFrame.Control_3_General, 10);
		this.DRIVER1.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVER1.setControlFramePeriod(ControlFrame.Control_3_General, 10);
		this.DRIVEL2.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVEL2.setControlFramePeriod(ControlFrame.Control_3_General, 10);
		this.DRIVER2.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVER2.setControlFramePeriod(ControlFrame.Control_3_General, 10);
	}
	public void drive(final double left, final double right)
	{
		this.DRIVE.tankDrive(left, right, false);
	}
	public void drive(final OI oi)
	{
		this.DRIVE.tankDrive(oi.getRightY() / 3, oi.getLeftY() / 3, false);
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new JoystickDrive());
	}
}