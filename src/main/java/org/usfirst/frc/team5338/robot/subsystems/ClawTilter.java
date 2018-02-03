package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.TiltClaw;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawTilter extends Subsystem
{
	private final WPI_TalonSRX dartTalon = new WPI_TalonSRX(31); //talon connected to the dart actuator
	private double potValue; //potentiometer value
	final double retracted = -190; // potentiometer value when the dart actuator is retracted
	final double extended = -126; //potentiometer value when dart actuator is extended
	boolean working;
	
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
		this.potValue = this.dartTalon.getSensorCollection().getAnalogIn(); //sense potentiometer value
		working = true; // change to talon sensing
		if(!working) { //if the talon isn't working
			
		}
		else if(Robot.oi.get(OI.Button.RAISE)) // raise the arm (extends the actuator)
		{
			if(this.potValue <= this.extended) // if the actuator isn't fully extended
			{ // we slow the actuator at the end to prevent a hard stop
				final double difference = Math.abs(this.potValue - this.extended); //how much further does the actuator need to extend
				final int distance = 15; //how soon should the actuator begin to slow down (while extending)
				final double lowSpeed = 0.10; //the speed to which the actuator will slow down to
				if(difference <= distance)
				{
					final double speedDifference = (0.90 - lowSpeed) / distance;//this code slows the speed in a linear fashion
					this.dartTalon.set(0.90 - (speedDifference * (distance - difference)));
				}
				else
				{
					this.dartTalon.set(0.90); //extend at a fast speed
				}
			}
			else
			{
				this.dartTalon.set(0); //turn off the actuator if it has gone past the limit. Prevents jamming.
			}
		}
		else if(Robot.oi.get(OI.Button.LOWER)) // lower the arm (retracts the actuator)
		{ //this code mirrors the code above that is used for extending
			if(this.potValue >= this.retracted) // if the actuator isn't fully retracted
			{
				final double difference = Math.abs(this.potValue - this.retracted);
				final int distance = 27;  //how soon should the actuator begin to slow down (while retracting)
				final double lowSpeed = 0.10;
				if(difference <= distance)
				{
					final double speedDifference = (0.90 - lowSpeed) / distance;
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
		else // if we aren't extending or retracting, stop the motor
		{
			this.dartTalon.set(0);
		}
		SmartDashboard.putNumber("pot", this.potValue); // display the potentiometer value in case the code needs to be fine tuned 
	}
}
