package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

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
	private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
	public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(2);
	public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(3);
	public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(4);
	// Contol-group objects used to move the left drive drive and right drive motors
	// in sync (two drive motors per side)
	private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
	private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
	// Creates a drive object that will define how the left and right motor sets are
	// configured (currently as an arcade drive)
	private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
	private double correctedPowerFromJoystick, turnIntensity;
	// Objects that control the shift and compressor mechanism
	private final Compressor driveCompressor = new Compressor(8);
	private final DoubleSolenoid driveSolenoid = new DoubleSolenoid(8, 3, 4);
	private boolean shiftedUp;

	// Use constructor for any pre-start initialization
	public DriveTrain()
	{
		super();
		this.driveCompressor.setClosedLoopControl(true);
		this.driveCompressor.start();
		this.driveSolenoid.set(DoubleSolenoid.Value.kForward);
		this.shiftedUp = false;
		this.configureAllTalons();
	}
	public void configureAllTalons()
	{
		this.DRIVEL1.configNeutralDeadband(0.001, 0);
		this.DRIVER1.configNeutralDeadband(0.001, 0);
		this.DRIVEL2.configNeutralDeadband(0.001, 0);
		this.DRIVER2.configNeutralDeadband(0.001, 0);
		this.DRIVEL1.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVEL1.setControlFramePeriod(ControlFrame.Control_3_General, 10);
		this.DRIVER1.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVER1.setControlFramePeriod(ControlFrame.Control_3_General, 10);
		this.DRIVEL2.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVEL2.setControlFramePeriod(ControlFrame.Control_3_General, 10);
		this.DRIVER2.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		this.DRIVER2.setControlFramePeriod(ControlFrame.Control_3_General, 10);
	}
	public SensorCollection[] getEncoders()
	{
		return new SensorCollection[] {this.DRIVEL1.getSensorCollection(), this.DRIVER2.getSensorCollection()};
	}
	// Runs the drive in arcade mode taking in the power magnitude toward the front
	// and diagonal
	public void drive(final double front, final double rotate)
	{
		this.DRIVE.arcadeDrive(front, rotate, false);
	}
	// Actual drive method called in Robot class
	public void drive(final OI oi)
	{
		// Uses directions and input to create a turn coefficient
		this.turnIntensity = Robot.oi.getLeft('X') * Math.abs(Robot.oi.getLeft('X'));
		// correctedPowerFromJoystick = -directionLeft * (a *
		// Math.pow(Robot.oi.getLeft('Y'), 3) * (1 - a) * (Robot.oi.getLeft('Y')));
		this.correctedPowerFromJoystick = (Math.pow(Robot.oi.getLeft('Y'), 3));
		// If the robot is driving straight or, in case it isn't driving straight, has a
		// speed <= 0.6, use arcade drive
		// else use curvature drive for better handling
		if(Robot.oi.getLeft('Y') >= 0.6)
		{
			this.DRIVE.curvatureDrive(this.correctedPowerFromJoystick, this.turnIntensity, false);
		}
		else
		{
			this.DRIVE.arcadeDrive(this.correctedPowerFromJoystick, this.turnIntensity, false);
		}
		/** Shift Control System **/
		if(this.shiftedUp)
		{
			this.turnIntensity *= .7; // If the gear is shifted up, reduce turn variable to reduce
										// over-turning/aggressive turning
		}
		if(Robot.oi.get(OI.Button.SHIFT_UP))
		{
			// If the user has allowed the gear to shift up, then change the solenoid to
			// allow for faster movement
			this.driveSolenoid.set(DoubleSolenoid.Value.kReverse);
			this.shiftedUp = true;
		}
		else if(Robot.oi.get(OI.Button.SHIFT_DOWN))
		{
			// If user forces the gear to shift down, only do so if power is < 20%
			if((Math.abs(this.m_left.get()) <= 0.20) && (Math.abs(this.m_right.get()) <= 0.20))
			{
				this.driveSolenoid.set(DoubleSolenoid.Value.kForward);
				this.shiftedUp = false;
			}
		}
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
		this.setDefaultCommand(new TankDriveWithJoysticks());
	}
}
