package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//
public class DriveTrain extends Subsystem
{
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(4);
	public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);
	private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
	private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	private final double throttle = 1.0;
	int directionRight, directionLeft;
	private final Compressor driveCompressor = new Compressor(5);
	private final DoubleSolenoid driveSolenoid = new DoubleSolenoid(5, 0, 1);
	private boolean shift;
	
	public DriveTrain()
	{
		super();
		this.driveCompressor.setClosedLoopControl(true);
		this.driveCompressor.start();
		this.driveSolenoid.set(DoubleSolenoid.Value.kForward);
		this.shift = false;
	}
	public void drive(final double front, final double rotate)
	{
		this.DRIVE.arcadeDrive(this.throttle * front, this.throttle * rotate, false);
	}
	public void drive(final OI oi)
	{
		/*
		 * a = 0.5; if ((this.throttle * Robot.oi.getRight('X')) < 0) { directionRight =
		 * 1; } else { directionRight = -1; } if ((this.throttle *
		 * Robot.oi.getLeft('Y')) < 0) { directionLeft = 1; } else { directionLeft = -1;
		 * } if (Robot.oi.get(OI.Button.SLOW)) { this.throttle = 0.5; } else {
		 * this.throttle = 1.0; }
		 */
		// speedPrimeRight = directionRight * (a * Math.pow(this.throttle *
		// oi.getRight(),3) * (1-a)*(this.throttle * oi.getRight()));
		// speedPrimeLeft = (a * Math.pow(this.throttle * oi.getLeft('Y'),2) *
		// (1-a)*(this.throttle * oi.getLeft('Y')));
		/*
		 * turn = Robot.oi.getLeft('X') * Math.abs(Robot.oi.getLeft('X'));
		 * speedPrimeLeft = -directionLeft * (a * Math.pow(Robot.oi.getLeft('Y'), 3) *
		 * (1 - a) * (Robot.oi.getLeft('Y'))); if (!Robot.oi.get(OI.Button.STRAIGHT)) {
		 * if (Robot.oi.getLeft('Y') >= 0.6) { this.DRIVE.curvatureDrive(speedPrimeLeft,
		 * turn, false); } else { this.DRIVE.arcadeDrive(speedPrimeLeft, turn, false); }
		 * } else { this.DRIVE.arcadeDrive(speedPrimeLeft, Robot.oi.getLeft('X'),
		 * false); }
		 */
		this.drive(Robot.oi.getLeft('Y'), Robot.oi.getLeft('Z'));
		if(this.shift)
		{
		}
		if(Robot.oi.get(OI.Button.SHIFT_UP))
		{
			this.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
			this.shift = true;
		}
		else if(Robot.oi.get(OI.Button.SHIFT_DOWN))
		{
			if((Math.abs(this.m_left.get()) <= 0.20) && (Math.abs(this.m_right.get()) <= 0.20))
			{
				this.driveSolenoid.set(DoubleSolenoid.Value.kForward);
				this.shift = false;
			}
		}
		SmartDashboard.putBoolean("shift", this.shift);
	}
	@Override
	public void initDefaultCommand()
	{
		// this.setDefaultCommand(new TankDriveWithJoysticks());
	}
}
