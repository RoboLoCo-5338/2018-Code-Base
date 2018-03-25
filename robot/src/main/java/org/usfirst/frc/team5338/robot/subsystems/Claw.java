package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.ClawControl;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw extends Subsystem
{
	private final WPI_TalonSRX DART = new WPI_TalonSRX(5);
	private final WPI_TalonSRX LEFT = new WPI_TalonSRX(6);
	private final WPI_TalonSRX RIGHT = new WPI_TalonSRX(7);
	private final DoubleSolenoid GRABBER = new DoubleSolenoid(8, 5, 6);
	private final DoubleSolenoid SHOOTER = new DoubleSolenoid(8, 0, 7);
	private double potValue; // potentiometer value
	private final double SCALE_VALUE = 40; // potentiometer value when the dart actuator is retracted
	private final double FLOOR_VALUE = 705; // potentiometer value when dart actuator is extended
	private final double SWITCH_VALUE = 275;
	private boolean clawClosed = true;
	private boolean shooterPosition = false;
	private int dartPosition = 3;
	private final double MIN_DART_SPEED = 0.125; // speed to which actuator slows
	private final double MAX_DART_SPEED = 0.99;

	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new ClawControl());
	}
	public Claw()
	{
		super();
		for(final WPI_TalonSRX talon : new WPI_TalonSRX[] {this.DART, this.LEFT, this.RIGHT})
		{
			Claw.configureTalon(talon);
		}
	}
	private static void configureTalon(final WPI_TalonSRX talon)
	{
		talon.configPeakCurrentLimit(100, 0);
		talon.configPeakCurrentDuration(1, 0);
		talon.configContinuousCurrentLimit(80, 0);
		talon.enableCurrentLimit(true);
		talon.configNeutralDeadband(0.001, 0);
		talon.setStatusFramePeriod(StatusFrame.Status_1_General, 5, 0);
		talon.setControlFramePeriod(ControlFrame.Control_3_General, 5);
	}
	public void setWheelSpeed(final double speed)
	{
		this.LEFT.set(speed);
		this.RIGHT.set(speed);
	}
	public void setDartPosition(final int position)
	{
		this.dartPosition = position;
		this.potValue = this.DART.getSensorCollection().getAnalogIn(); // get the analog value of the talon on
		this.changeDartPosition();
	}
	public int getDartPosition()
	{
		return this.dartPosition;
	}
	public void changeDartPosition()
	{
		// which the Dart Actuator runs
		switch(this.dartPosition)
		{
			case 3:
				if(this.potValue > this.SCALE_VALUE)
				{ // if the user wants to retract and the claw hasn't hit minimum value
					final double distanceToMin = Math.abs(this.potValue - this.SCALE_VALUE);
					final int slowDownRange = 125; // declares that the actuator will slow 270 points away from minimum
													// value
					if(distanceToMin <= slowDownRange)
					{
						/**
						 * model the slowing down of the actuator as y = mx + b if we are within
						 * slow-down range
						 **/
						final double deceleration = (this.MAX_DART_SPEED - this.MIN_DART_SPEED) / slowDownRange; // deceleration
						// rate as
						// calculated in raising
						// portion
						// of code
						// calculate new speed as was done in raising portion of code, but multiply by
						// -1 to show direction change to lowering
						final double newSpeed =
										(this.MAX_DART_SPEED - (deceleration * (slowDownRange - distanceToMin))) * -1;
						this.DART.set(newSpeed);
					}
					else
					{
						// if we are beyond slow-down range, slow down at max speed
						this.DART.set(-1 * this.MAX_DART_SPEED);
					}
				}
				else
				{
					this.DART.set(0);// stop the talon if the potentiometer value is less than the value for
										// retraction.
					// prevents jamming the actuator
				}
				break;
			case 2:
				if(this.potValue > this.SWITCH_VALUE)
				{ // if the user wants to retract and the claw hasn't hit minimum value
					final double distanceToMin = Math.abs(this.potValue - this.SWITCH_VALUE);
					final int slowDownRange = 125; // declares that the actuator will slow 270 points away from minimum
													// value
					if(distanceToMin <= slowDownRange)
					{
						/**
						 * model the slowing down of the actuator as y = mx + b if we are within
						 * slow-down range
						 **/
						final double deceleration = (this.MAX_DART_SPEED - this.MIN_DART_SPEED) / slowDownRange; // deceleration
						// rate as
						// calculated in raising
						// portion
						// of code
						// calculate new speed as was done in raising portion of code, but multiply by
						// -1 to show direction change to lowering
						final double newSpeed =
										(this.MAX_DART_SPEED - (deceleration * (slowDownRange - distanceToMin))) * -1;
						this.DART.set(newSpeed);
					}
					else
					{
						// if we are beyond slow-down range, slow down at max speed
						this.DART.set(-1 * this.MAX_DART_SPEED);
					}
				}
				else if(this.potValue < this.SWITCH_VALUE)
				{ // if the user wants to raise the claw and the claw hasn't hit its max yet
					final double distanceToMax = Math.abs(this.potValue - this.SWITCH_VALUE); // the amount needed to
																								// extend to max
					final int slowDownRange = 75; // declares that actuator will slow 150 points away from the
													// actuator's
													// maximum value
					if(distanceToMax <= slowDownRange)
					{
						/**
						 * model the slowing down of the actuator as y = mx + b if we are within
						 * slow-down range
						 **/
						final double deceleration = (this.MAX_DART_SPEED - this.MIN_DART_SPEED) / slowDownRange;
						final double newSpeed = this.MAX_DART_SPEED - (deceleration * (slowDownRange - distanceToMax));
						this.DART.set(newSpeed);
					}
					else
					{
						// If we have more distance to the maximum than the 15 point limit, slow down
						// the actuator at maximum speed until we need to decelerate
						this.DART.set(this.MAX_DART_SPEED);
					}
				}
				else
				{
					this.DART.set(0);// stop the talon if the potentiometer value is less than the value for
										// retraction.
					// prevents jamming the actuator
				}
				break;
			case 1:
				if(this.potValue < this.FLOOR_VALUE)
				{ // if the user wants to raise the claw and the claw hasn't hit its max yet
					final double distanceToMax = Math.abs(this.potValue - this.FLOOR_VALUE); // the amount needed to
																								// extend to max
					final int slowDownRange = 75; // declares that actuator will slow 150 points away from the
													// actuator's maximum value
					if(distanceToMax <= slowDownRange)
					{
						/**
						 * model the slowing down of the actuator as y = mx + b if we are within
						 * slow-down range
						 **/
						final double deceleration = (this.MAX_DART_SPEED - this.MIN_DART_SPEED) / slowDownRange;
						final double newSpeed = this.MAX_DART_SPEED - (deceleration * (slowDownRange - distanceToMax));
						this.DART.set(newSpeed);
					}
					else
					{
						// If we have more distance to the maximum than the 15 point limit, slow down
						// the actuator at maximum speed until we need to decelerate
						this.DART.set(this.MAX_DART_SPEED);
					}
				}
				else
				{
					this.DART.set(0); // stop the talon if the potentiometer value is greater than the value for
										// extension.
					// prevents jamming the actuator
				}
				break;
			default:
				break;
		}
	}
	/**
	 * Method: tilt
	 *
	 * @param oi
	 *            Action: takes in the OI and takes care of all claw tilting actions
	 *            based on Operator Input
	 */
	public void control(final OI oi)
	{
		this.potValue = this.DART.getSensorCollection().getAnalogIn(); // get the analog value of the talon on
																		// which the Dart Actuator runs
		if(oi.get(OI.Button.CLOSE_CLAW))
		{
			this.GRABBER.set(DoubleSolenoid.Value.kReverse);
			this.clawClosed = true;
		}
		else if(oi.get(OI.Button.OPEN_CLAW))
		{
			this.GRABBER.set(DoubleSolenoid.Value.kForward);
			this.clawClosed = false;
		}
		else
		{
			this.GRABBER.set(DoubleSolenoid.Value.kOff);
		}
		if(oi.get(OI.Button.EXTEND_SHOOTER))
		{
			this.SHOOTER.set(DoubleSolenoid.Value.kForward);
			this.shooterPosition = true;
		}
		else
		{
			this.SHOOTER.set(DoubleSolenoid.Value.kReverse);
			this.shooterPosition = false;
		}
		if(oi.get(OI.Button.INTAKE_CUBE))
		{
			this.setWheelSpeed(-0.35);
		}
		else if(oi.get(OI.Button.OUTTAKE_CUBE))
		{
			this.setWheelSpeed(0.30);
		}
		else if(oi.get(OI.Button.POWER_SHOOTER))
		{
			this.setWheelSpeed(0.99);
		}
		else
		{
			this.setWheelSpeed(0);
		}
		if(oi.get(OI.Button.DART_FLOOR))
		{
			this.dartPosition = 1;
		}
		else if(oi.get(OI.Button.DART_SWITCH))
		{
			this.dartPosition = 2;
		}
		else if(oi.get(OI.Button.DART_SCALE))
		{
			this.dartPosition = 3;
		}
		this.changeDartPosition();
		SmartDashboard.putBoolean("Shooter Status", this.shooterPosition);
		SmartDashboard.putNumber("DART Position", this.potValue);
		SmartDashboard.putBoolean("Claw Status", this.clawClosed);
	}
}
