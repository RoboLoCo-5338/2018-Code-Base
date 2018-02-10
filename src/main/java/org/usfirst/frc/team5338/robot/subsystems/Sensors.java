package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.EncoderCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors extends Subsystem
{
	private double encoderValue;
	private double encoderLeft, encoderRight = 0.0;
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);
	
	public Sensors()
	{
		super();
	}
	public void returnRotation(final OI oi)
	{
		this.encoderLeft = this.DRIVEL1.getSensorCollection().getQuadraturePosition();
		this.encoderRight = this.DRIVER2.getSensorCollection().getQuadraturePosition();
		SmartDashboard.putNumber("encoder left", this.encoderLeft);
		SmartDashboard.putNumber("encoder right", this.encoderRight);
	}
	public double measureEncoder(final WPI_TalonSRX encoderTalon)
	{
		this.encoderValue = encoderTalon.getSensorCollection().getQuadraturePosition();
		return this.encoderValue;
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new EncoderCommand());
	}
}
