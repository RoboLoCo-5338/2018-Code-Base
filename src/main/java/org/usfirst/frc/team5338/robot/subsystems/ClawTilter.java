package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.TiltClaw;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawTilter extends Subsystem
{
	private final WPI_TalonSRX dartTalon = new WPI_TalonSRX(31);
	private double potValue;
	final double retracted = -190;
	final double extended = -126;
	
	public ClawTilter()
	{
		super();
	}
	//
	@Override
	public void initDefaultCommand()
	{
		//this.setDefaultCommand(new TiltClaw());
	}
	public void tilt(final OI oi)
	{
		this.potValue = this.dartTalon.getSensorCollection().getAnalogIn();
		if(Robot.oi.get(OI.Button.RAISE))
		{
			if(this.potValue <= this.extended)
			{ // extend
				final double difference = Math.abs(this.potValue - this.extended);
				final int distance = 15;
				if(difference <= distance)
				{
					final double speedDifference = (0.90 - 0.10) / distance;
					this.dartTalon.set(0.90 - (speedDifference * (distance - difference)));
				}
				else
				{
					this.dartTalon.set(0.90);
				}
			}
			else
			{
				this.dartTalon.set(0);
			}
		}
		else if(Robot.oi.get(OI.Button.LOWER))
		{
			if(this.potValue >= this.retracted)
			{ // retract
				final double difference = Math.abs(this.potValue - this.retracted);
				final int distance = 27;
				if(difference <= distance)
				{
					final double speedDifference = (0.90 - 0.10) / distance;
					this.dartTalon.set(-(0.90 - (speedDifference * (distance - difference))));
				}
				else
				{
					this.dartTalon.set(-0.90);
				}
			}
			else
			{
				this.dartTalon.set(0);
			}
		}
		else
		{
			this.dartTalon.set(0);
		}
		SmartDashboard.putNumber("pot", this.potValue);
	}
}
