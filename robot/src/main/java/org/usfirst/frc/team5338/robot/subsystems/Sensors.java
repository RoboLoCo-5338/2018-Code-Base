package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Sensors extends Subsystem
{
	private final SensorCollection[] encoders = Robot.drivetrain.getEncoders();
	public final AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) (200));
	private double right_rotations, left_rotations, right_prev, right_current, left_prev, left_current;
	
	public Sensors()
	{
		super();
		while(this.ahrs.isCalibrating() || this.ahrs.isMagnetometerCalibrated())
		{
			// Calibrating NavX
		}
		this.right_rotations = 0;
		this.left_rotations = 0;
		this.resetSensors();
	}
	public void resetSensors()
	{
		this.encoders[0].setQuadraturePosition(0, 0);
		this.encoders[1].setQuadraturePosition(0, 0);
		this.right_prev = 0;
		this.left_prev = 0;
		this.right_current = 0;
		this.left_current = 0;
		this.ahrs.reset();
		this.ahrs.zeroYaw();
	}
	private void updateEncoders()
	{
		this.right_prev = this.right_current;
		this.right_current = Math.abs(this.encoders[0].getQuadraturePosition());
		this.left_prev = this.left_current;
		this.left_current = Math.abs(this.encoders[1].getQuadraturePosition());
		// System.out.print("right_current: ");
		// System.out.println(this.right_current);
		// System.out.print("left_current: ");
		// System.out.println(this.left_current);
	}
	public double[] distances()
	{
		this.updateEncoders();
		this.right_rotations = (this.right_current - this.right_prev) / 4096.0; // 12 bit data
		this.left_rotations = (this.left_current - this.left_prev) / 4096.0; // 12 bit data
		return new double[] {Math.abs(this.left_rotations), Math.abs(this.right_rotations)}; // Both inches measurements
	}
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(null);
	}
}