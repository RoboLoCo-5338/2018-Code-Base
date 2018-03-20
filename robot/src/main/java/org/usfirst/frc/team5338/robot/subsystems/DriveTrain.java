package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.JoystickDrive;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem
{
	// Field variables that we will use
	// Talons: motor controllers that we use on the robot
	private final WPI_TalonSRX LEFT_1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX LEFT_2 = new WPI_TalonSRX(2);
	public final WPI_TalonSRX RIGHT_1 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX RIGHT_2 = new WPI_TalonSRX(4);
	// Contol-group objects used to move the left drive drive and right drive motors
	// in sync (two drive motors per side)
	private final SpeedControllerGroup LEFTSIDE = new SpeedControllerGroup(this.LEFT_1, this.LEFT_2);
	private final SpeedControllerGroup RIGHTSIDE = new SpeedControllerGroup(this.RIGHT_1, this.RIGHT_2);
	// Creates a drive object that will define how the left and right motor sets are
	// configured (currently as an arcade drive)
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.LEFTSIDE, this.RIGHTSIDE);
	// Objects that control the shift and compressor mechanism
	private final Compressor COMPRESSOR = new Compressor(8);
	private final DoubleSolenoid SHIFTER = new DoubleSolenoid(8, 3, 4);
	
	public DriveTrain()
	{
		super();
		this.COMPRESSOR.setClosedLoopControl(true);
		this.COMPRESSOR.start();
		this.SHIFTER.set(DoubleSolenoid.Value.kForward);
		for(final WPI_TalonSRX talon : new WPI_TalonSRX[] {this.LEFT_1, this.LEFT_2, this.RIGHT_1, this.RIGHT_2})
		{
			DriveTrain.configureTalon(talon);
		}
	}
	private static void configureTalon(final WPI_TalonSRX talon)
	{
		talon.configNeutralDeadband(0.001, 0);
		talon.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		talon.setControlFramePeriod(ControlFrame.Control_3_General, 5);
	}
	public void drive(final double left, final double right)
	{
		this.DRIVE.tankDrive(left, right, false);
	}
	public void drive(final OI oi)
	{
		this.DRIVE.tankDrive(oi.getRightY() / 3, oi.getLeftY() / 3, false);
	}
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new JoystickDrive());
	}
}