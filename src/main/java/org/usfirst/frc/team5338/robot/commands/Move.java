package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Move extends Command
{
	double distance, targetDistance, encoderLeft, encoderRight, numRotations;
	double[] beginningEncoderData;
	
	public Move(final double distance1)
	{
		super();
		this.distance = distance1;
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.beginningEncoderData = Robot.sensors.returnRotation();
		this.numRotations = (this.distance) / (6 * Math.PI);
		this.targetDistance = 4000;// (this.numRotations * 4000);
	}
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("distanceFinal", this.distance);
		final double[] encoderData = Robot.sensors.returnRotation();
		this.encoderLeft = encoderData[0];
		this.encoderRight = encoderData[1];
		System.out.print("Target: ");
		System.out.println(this.targetDistance);
		System.out.print("Current: ");
		System.out.println(Math.abs(this.encoderRight));
		if(Math.abs(this.encoderRight) < this.targetDistance)
		{
			Robot.drivetrain.drive(0.15, 0);
		}
		else
		{
			Robot.drivetrain.drive(0, 0);
		}
		//
		// double difference = this.encoderLeft - this.targetDistance;
		// Math.abs(difference);
		// if(difference > 0)
		// {
		// return;
		// }
		// difference = Math.abs(difference);
		// final double distance1 = 3000;
		// final double speedDifference = 0.20 / distance1;
		// if(difference <= distance1)
		// {
		// Robot.drivetrain.drive(0.20 - (speedDifference * difference), 0);
		// }
		// else
		// {
		// Robot.drivetrain.drive(0.20, 0);
		// }
		// }
		// else
		// {
		// Robot.drivetrain.drive(0, 0);
		// }
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
