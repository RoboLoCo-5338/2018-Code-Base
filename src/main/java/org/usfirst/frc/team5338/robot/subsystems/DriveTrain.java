package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//
public class DriveTrain extends Subsystem
{
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(4);
	public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(2);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(1);
	private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
	private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	private double throttle = 1.0;
	private double speedPrimeLeft, turn, a;
	int directionRight, directionLeft;

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
		a = 0.2;

		if((this.throttle * oi.getRight('Y')) < 0) {
			directionRight = 1;
		} else {
			directionRight = -1;
		}

		if((this.throttle * oi.getLeft('Y')) < 0) {
			directionLeft = 1;
		} else {
			directionLeft = -1;
		}

		if(oi.get(OI.Button.SLOW))
		{
			this.throttle = 0.5;
		}
		else
		{
			this.throttle = 1.0;
		}
//
		//speedPrimeRight =  directionRight * (a * Math.pow(this.throttle * oi.getRight(),3) * (1-a)*(this.throttle * oi.getRight()));
		//speedPrimeLeft =  (a * Math.pow(this.throttle * oi.getLeft('Y'),2) * (1-a)*(this.throttle * oi.getLeft('Y')));

        turn = Robot.oi.getLeft('X')*Math.abs(Robot.oi.getLeft('X'));
        speedPrimeLeft = -directionLeft * (a * Math.pow(oi.getLeft('Y'),3) * (1-a)*(oi.getLeft('Y')));

		if(!oi.get(OI.Button.STRAIGHT))
        {
		    if(oi.getLeft('Y') >= 0.6) {
                this.DRIVE.curvatureDrive(speedPrimeLeft, turn, false);
            } else {
                this.DRIVE.arcadeDrive(speedPrimeLeft, turn, false);
            }
		}
		else
		{
			this.DRIVE.arcadeDrive(speedPrimeLeft, oi.getLeft('X'), false);
		}
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new TankDriveWithJoysticks());
	}
}