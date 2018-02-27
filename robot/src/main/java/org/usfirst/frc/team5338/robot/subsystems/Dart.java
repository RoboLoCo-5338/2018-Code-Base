package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.MoveArm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Dart extends Subsystem
{
	private final WPI_TalonSRX dartTalon = new WPI_TalonSRX(5); // talon connected to the dart actuator
	private double potValue; // potentiometer value
	final double retractedValue = 65; // potentiometer value when the dart actuator is retracted
	final double extendedValue = 705; // potentiometer value when dart actuator is extended

	@Override
	protected void initDefaultCommand()
	{ // default command required by Subsystem class. Not being modified
		this.setDefaultCommand(new MoveArm());
	}
	public Dart()
	{
		super();
	}// Default Constructor
	/**
	 * Method: tilt
	 *
	 * @param oi
	 *            Action: takes in the OI and takes care of all claw tilting actions
	 *            based on Operator Input
	 */
	public void control(final OI oi)
	{
		this.potValue = this.dartTalon.getSensorCollection().getAnalogIn(); // get the analog value of the talon on
																			// which the Dart Actuator runs
		final double throttlePosition = 65 + ((640 * (oi.getLeftThrottle() - 1)) / -2);
		if((this.potValue > this.retractedValue) && (this.potValue < this.extendedValue))
		{
			if((Math.abs(this.potValue - throttlePosition) > 5) && (this.potValue < throttlePosition))
			{
				this.dartTalon.set(0.15);
			}
			else if((Math.abs(this.potValue - throttlePosition) > 5) && (this.potValue > throttlePosition))
			{
				this.dartTalon.set(-0.15);
			}
			else
			{
				this.dartTalon.set(0.0);
			}
		}
	}
}