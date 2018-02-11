package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Sensors extends Subsystem
{
	private final SensorCollection[] encoders = Robot.drivetrain.getEncoders();
	public static final AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) (200));
	
	public Sensors()
	{
		super();
		while(Sensors.ahrs.isCalibrating() || Sensors.ahrs.isMagnetometerCalibrated())
		{
		}
	}
	public void zeroEncoders()
	{
		this.encoders[0].setQuadraturePosition(0, 0);
		this.encoders[1].setQuadraturePosition(0, 0);
	}
	public double[] returnRotation()
	{
		return new double[] {this.encoders[0].getQuadraturePosition(), this.encoders[1].getQuadraturePosition()};
	}
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(null);
	}
}