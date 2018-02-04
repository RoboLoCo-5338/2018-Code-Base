package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.JoystickDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem
{
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(4);
	public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(2);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(1);
	private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
	private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	
	public DriveTrain()
	{
		super();
	}
	public void drive(final double left, final double right)
	{
		this.DRIVE.tankDrive(left, right, true);
	}
	public void drive(final OI oi)
	{
		this.DRIVE.tankDrive(oi.getLeft() / 4.0, oi.getRight() / 4.0, true);
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new JoystickDrive());
	}
}