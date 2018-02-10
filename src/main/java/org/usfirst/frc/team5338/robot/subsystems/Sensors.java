package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.GetSensorData;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors extends Subsystem
{
	private double encoderValue;
	private double encoderLeft, encoderRight = 0.0;
	public static final AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) (200));

	public Sensors()
	{
		super();
		while(Sensors.ahrs.isCalibrating() || Sensors.ahrs.isMagnetometerCalibrated())
		{
		}
	}
	public void returnRotation()
	{
		this.DRIVEL1.getSensorCollection().
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
		this.setDefaultCommand(new GetSensorData());
	}
}
