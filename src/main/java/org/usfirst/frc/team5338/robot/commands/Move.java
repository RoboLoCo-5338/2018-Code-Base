package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Move extends Command
{
	double distance, targetRotationsLeft, targetRotationsRight, numRotations;
	double[] beginningEncoderData;
	
	public Move(final double input)
	{
		super();
		this.requires(Robot.drivetrain);
		this.requires(Robot.sensors);
		this.distance = input;
	}
	@Override
	protected void initialize()
	{
		super.initialize();
		this.targetRotationsLeft = (this.distance);
		this.targetRotationsRight = (this.distance);
		Robot.sensors.zeroEncoders();
	}
	@Override
	protected void execute()
	{
		final double[] distanceTravelled = Robot.sensors.distances();
		this.targetRotationsRight -= Math.abs(distanceTravelled[1]);
		this.targetRotationsLeft -= Math.abs(distanceTravelled[0]);
		double right, left;
		if(this.targetRotationsRight > 0)
		{
			right = 0.165;
		}
		else
		{
			right = 0;
		}
		if(this.targetRotationsLeft > 0)
		{
			left = 0.15;
		}
		else
		{
			left = 0;
		}
		Robot.drivetrain.tankDrive(left, right);
		// }
		// SmartDashboard.putNumber("distanceFinal", this.distance);
		// final double[] encoderData = Robot.sensors.distances();
		// this.encoderLeft = encoderData[0];
		// this.encoderRight = encoderData[1];
		// System.out.print("Target: ");
		// System.out.println(this.targetDistance);
		// System.out.print("Current: ");
		// System.out.println(Math.abs(this.encoderRight));
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
