package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.JoystickDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem
{
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(4);
	public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);
	private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
	private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	private final Compressor driveCompressor = new Compressor(5);
	private final DoubleSolenoid driveSolenoid = new DoubleSolenoid(5, 0, 1);

	public DriveTrain()
	{
		super();
		this.driveCompressor.setClosedLoopControl(true);
		this.driveCompressor.start();
		this.driveSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	public void drive(final double left, final double right)
	{
		this.DRIVE.tankDrive(left, right, false);
	}
	public void drive(final OI oi)
	{
		this.DRIVE.tankDrive(oi.getRight() / 3.5, oi.getLeft() / 3.5, false);
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new JoystickDrive());
	}
}