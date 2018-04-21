package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.JoystickControl;
import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Class in which Robot calls to perform all functions: specifically Drive Train
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
	private final SpeedControllerGroup LEFT_SIDE = new SpeedControllerGroup(this.LEFT_1, this.LEFT_2);
	private final SpeedControllerGroup RIGHT_SIDE = new SpeedControllerGroup(this.RIGHT_1, this.RIGHT_2);
	// Creates a drive object that will define how the left and right motor sets are
	// configured (currently as an arcade drive)
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.LEFT_SIDE, this.RIGHT_SIDE);
	// Objects that control the shift and compressor mechanism
	private final Compressor COMPRESSOR = new Compressor(8);
	private final DoubleSolenoid SHIFTER = new DoubleSolenoid(8, 1, 2);
	private boolean shiftedUp;
	
	// Use constructor for any pre-start initialization
	public DriveTrain()
	{
		super();
		this.COMPRESSOR.setClosedLoopControl(true);
		this.COMPRESSOR.start();
		this.SHIFTER.set(DoubleSolenoid.Value.kForward);
		this.shiftedUp = false;
		for(final WPI_TalonSRX talon : new WPI_TalonSRX[] {this.LEFT_1, this.LEFT_2, this.RIGHT_1, this.RIGHT_2})
		{
			DriveTrain.configureTalon(talon);
		}
		this.SHIFTER.set(DoubleSolenoid.Value.kForward);
	}
	private static void configureTalon(final WPI_TalonSRX talon)
	{
		talon.configPeakCurrentLimit(100, 0);
		talon.configPeakCurrentDuration(3, 0);
		talon.configContinuousCurrentLimit(80, 0);
		talon.enableCurrentLimit(true);
		talon.configNeutralDeadband(0.001, 0);
		talon.setStatusFramePeriod(StatusFrame.Status_1_General, 5, 0);
		talon.setControlFramePeriod(ControlFrame.Control_3_General, 5);
	}
	public void shift(final boolean state)
	{
		if(state)
		{
			this.SHIFTER.set(DoubleSolenoid.Value.kReverse);
			this.shiftedUp = true;
		}
		else
		{
			this.SHIFTER.set(DoubleSolenoid.Value.kForward);
			this.shiftedUp = false;
		}
	}
	public SensorCollection[] getEncoders()
	{
		return new SensorCollection[] {this.LEFT_1.getSensorCollection(), this.RIGHT_2.getSensorCollection()};
	}
	// Runs the drive in tank mode
	public void drive(final double left, final double right)
	{
		this.DRIVE.tankDrive(left, right);
	}
	// Actual drive method called in Robot class
	public void drive(final OI oi)
	{
		float multiplier = Robot.slowMode? 2 : 3;
		if(Robot.arcadeMode)
			this.DRIVE.arcadeDrive(oi.getRightJoystick('Y') * multiplier / 4, oi.getRightJoystick('X') / 1.5);
		else
		{
		if(this.shiftedUp)
		{
			this.DRIVE.tankDrive((oi.getLeftJoystick('Y') * multiplier) / 5, (oi.getRightJoystick('Y') * multiplier) / 5, false);
		}
		else
		{
			this.DRIVE.tankDrive((oi.getLeftJoystick('Y') * multiplier) / 4, (oi.getRightJoystick('Y') * multiplier) / 4, false);
		}
		}
		
		if(oi.get(OI.Button.FAST_MODE))
		{
			Robot.slowMode = false;
			Robot.functionsEnabled = true;
			Robot.arcadeMode = false;
		}
		if(oi.get(OI.Button.SLOW_MODE))
		{
			Robot.slowMode = true;
			Robot.functionsEnabled = true;
			Robot.arcadeMode = false;
		}
		if(oi.get(OI.Button.SAFE_MODE))
		{
			Robot.slowMode = true;
			Robot.functionsEnabled = false;
			Robot.arcadeMode = false;
		}
		if(oi.get(OI.Button.ARCADE_MODE))
		{
			Robot.arcadeMode = true;
		}
		
		// if(Robot.oi.get(OI.Button.SHIFT_UP))
		// {
		// // If the user has allowed the gear to shift up, then change the solenoid to
		// // allow for faster movement
		// this.SHIFTER.set(DoubleSolenoid.Value.kReverse);
		// this.shiftedUp = true;
		// }
		// else if(Robot.oi.get(OI.Button.SHIFT_DOWN))
		// {
		// // If user forces the gear to shift down, only do so if power is < 25%
		// if((Math.abs(this.LEFT_SIDE.get()) <= 0.25) &&
		// (Math.abs(this.RIGHT_SIDE.get()) <= 0.25))
		// {
		// this.SHIFTER.set(DoubleSolenoid.Value.kForward);
		// this.shiftedUp = false;
		// }
		// }
		/**
		 * IMPORTANT: Due to motor mirroring Forward: Left = +, Right = - Backward: Left
		 * = -, Right = +
		 */
		SmartDashboard.putBoolean("Shift Status", this.shiftedUp);
		SmartDashboard.putData("Drivetrain Status", this.DRIVE);
	}
	// Default drive command will be a general tank drive instead of arcade drive
	@Override
	public void initDefaultCommand()
	{
		this.setDefaultCommand(new JoystickControl());
	}
}
