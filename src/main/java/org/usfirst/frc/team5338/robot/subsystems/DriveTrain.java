package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem
{
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	private final TalonSRX DRIVEL1 = new TalonSRX(4);
	public final TalonSRX DRIVEL2 = new TalonSRX(3);
	public final TalonSRX DRIVER1 = new TalonSRX(2);
	public final TalonSRX DRIVER2 = new TalonSRX(1);
	private final SpeedControllerGroup m_left =
					new SpeedControllerGroup((SpeedController) this.DRIVEL1, (SpeedController) this.DRIVEL2);
	private final SpeedControllerGroup m_right =
					new SpeedControllerGroup((SpeedController) this.DRIVER1, (SpeedController) this.DRIVER2);
	private double throttle = 1.0;

	public DriveTrain()
	{
		super();
	}
	public void drive(final double left, final double right)
	{
		this.DRIVE.tankDrive(this.throttle * left, this.throttle * right, false);
	}
	public void drive(final OI oi)
	{
		if(oi.get(OI.Button.SLOW))
		{
			this.throttle = 0.5;
		}
		else
		{
			this.throttle = 1.0;
		}
		this.DRIVE.tankDrive(this.throttle * oi.getRight(), this.throttle * oi.getLeft(), false);
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new TankDriveWithJoysticks());
	}
}