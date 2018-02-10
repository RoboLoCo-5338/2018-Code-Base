package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Move extends Command
{
	double distance, encoderLeft, encoderRight, encoderRightBegin, encoderLeftBegin, numRotations, initial;
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);

	public Move(final double input)
	{
		super();
		this.distance = input;
		this.requires(Robot.drivetrain);
		this.requires(Robot.encoders);
		Robot.drivetrain.setShift(DoubleSolenoid.Value.kForward);
		this.encoderLeftBegin = Robot.encoders.measureEncoder(this.DRIVEL1);
		this.encoderRightBegin = Robot.encoders.measureEncoder(this.DRIVER2);
		this.numRotations = (this.distance) / (12 * Math.PI);
		this.initial = Robot.ahrs.getYaw();
	}
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("distanceFinal", this.distance);
		this.encoderLeft = Robot.encoders.measureEncoder(this.DRIVEL1);
		this.encoderRight = Robot.encoders.measureEncoder(this.DRIVER2);
		if(this.encoderLeft < (this.encoderLeftBegin + (this.numRotations * 4096)))
		{
			final double difference = Math.abs(this.encoderLeft - (this.encoderLeftBegin + (this.numRotations * 4096)));
			final double distance1 = 100;
			if(difference <= distance1)
			{
				final double speedDifference = (0.25 - 0.1) / distance1;
				Robot.drivetrain.drive(0.25 - (speedDifference * (distance1 - difference)), 0);
			}
			else
			{
				Robot.drivetrain.drive(0.25, 0);
			}
		}
		else
		{
			Robot.drivetrain.drive(0, 0);
		}
		//
	}
	@Override
	protected boolean isFinished()
	{
		return false;
	}
	@Override
	protected void end()
	{
		Robot.drivetrain.drive(0.0, 0.0);
	}
}
