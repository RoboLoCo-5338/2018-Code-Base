package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.DisplaySensors;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors extends Subsystem
{
	private final SensorCollection[] ENCODERS = Robot.drivetrain.getEncoders();
	public final AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) (200));
	private double right_rotations, left_rotations, right_prev, right_current, left_prev, left_current;
	private final PowerDistributionPanel PDP = new PowerDistributionPanel();
	
	public Sensors()
	{
		super();
		while(this.ahrs.isCalibrating() || this.ahrs.isMagnetometerCalibrated())
		{
			; // Calibrating NavX
		}
		this.right_rotations = 0;
		this.left_rotations = 0;
		this.ahrs.reset();
		this.ahrs.zeroYaw();
		this.resetEncoders();
	}
	public void resetEncoders()
	{
		this.ENCODERS[0].setQuadraturePosition(0, 0);
		this.ENCODERS[1].setQuadraturePosition(0, 0);
		this.right_prev = 0;
		this.left_prev = 0;
		this.right_current = 0;
		this.left_current = 0;
	}
	private void updateEncoders()
	{
		this.right_prev = this.right_current;
		this.right_current = Math.abs(this.ENCODERS[0].getQuadraturePosition());
		this.left_prev = this.left_current;
		this.left_current = Math.abs(this.ENCODERS[1].getQuadraturePosition());
	}
	public double[] distances()
	{
		this.updateEncoders();
		this.right_rotations = (this.right_current - this.right_prev) / 4096.0; // 12 bit data
		this.left_rotations = (this.left_current - this.left_prev) / 4096.0; // 12 bit data
		return new double[] {Math.abs(this.left_rotations), Math.abs(this.right_rotations)}; // Both inches measurements
	}
	public void displayVoltage()
	{
		SmartDashboard.putNumber("System Voltage", this.PDP.getVoltage());
	}
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new DisplaySensors());
	}
}