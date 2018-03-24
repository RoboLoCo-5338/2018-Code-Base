package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.ClawControl;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw extends Subsystem
{
	private final WPI_TalonSRX dartTalon = new WPI_TalonSRX(5);
	private final WPI_TalonSRX leftMotor = new WPI_TalonSRX(6);
	private final WPI_TalonSRX rightMotor = new WPI_TalonSRX(7);
	private final DoubleSolenoid grabber = new DoubleSolenoid(8, 5, 6);
	private final DoubleSolenoid shooter = new DoubleSolenoid(8, 0, 7);
	private double potValue; // potentiometer value
	final double scaleValue = 50; // potentiometer value when the dart actuator is retracted
	final double floorValue = 700; // potentiometer value when dart actuator is extended
	final double switchValue = 275;
	private boolean clawClosed = true;
	private boolean shooterPosition = false;
	private int dartPosition = 3;
	// TWEAK BASED ON NEW DART RATIO
	private final double lowSpeed = 0.25; // speed to which actuator slows
	private final double maxSpeed = 0.99;
	
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new ClawControl());
	}
	public Claw()
	{
		super();
	}
	public void setWheelSpeed(final double speed)
	{
		this.leftMotor.set(speed);
		this.rightMotor.set(speed);
	}
	public void setDartPosition(final int position)
	{
		this.dartPosition = position;
		this.potValue = this.dartTalon.getSensorCollection().getAnalogIn(); // get the analog value of the talon on
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
				if(this.potValue > this.scaleValue)
				{ // if the user wants to retract and the claw hasn't hit minimum value
					final double distanceToMin = Math.abs(this.potValue - this.scaleValue);
					final int slowDownRange = 125; // declares that the actuator will slow 270 points away from minimum
													// value
					if(distanceToMin <= slowDownRange)
					{
						/**
						 * model the slowing down of the actuator as y = mx + b if we are within
						 * slow-down range
						 **/
						final double deceleration = (this.maxSpeed - this.lowSpeed) / slowDownRange; // deceleration
																										// rate as
						// calculated in raising
						// portion
						// of code
						// calculate new speed as was done in raising portion of code, but multiply by
						// -1 to show direction change to lowering
						final double newSpeed = (this.maxSpeed - (deceleration * (slowDownRange - distanceToMin))) * -1;
						this.dartTalon.set(newSpeed);
					}
					else
					{
						// if we are beyond slow-down range, slow down at max speed
						this.dartTalon.set(-1 * this.maxSpeed);
					}
				}
				else
				{
					this.dartTalon.set(0);// stop the talon if the potentiometer value is less than the value for
											// retraction.
					// prevents jamming the actuator
				}
				break;
			case 2:
				if(this.potValue > this.switchValue)
				{ // if the user wants to retract and the claw hasn't hit minimum value
					final double distanceToMin = Math.abs(this.potValue - this.switchValue);
					final int slowDownRange = 125; // declares that the actuator will slow 270 points away from minimum
													// value
					if(distanceToMin <= slowDownRange)
					{
						/**
						 * model the slowing down of the actuator as y = mx + b if we are within
						 * slow-down range
						 **/
						final double deceleration = (this.maxSpeed - this.lowSpeed) / slowDownRange; // deceleration
																										// rate as
						// calculated in raising
						// portion
						// of code
						// calculate new speed as was done in raising portion of code, but multiply by
						// -1 to show direction change to lowering
						final double newSpeed = (this.maxSpeed - (deceleration * (slowDownRange - distanceToMin))) * -1;
						this.dartTalon.set(newSpeed);
					}
					else
					{
						// if we are beyond slow-down range, slow down at max speed
						this.dartTalon.set(-1 * this.maxSpeed);
					}
				}
				else if(this.potValue < this.switchValue)
				{ // if the user wants to raise the claw and the claw hasn't hit its max yet
					final double distanceToMax = Math.abs(this.potValue - this.switchValue); // the amount needed to
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
						final double deceleration = (this.maxSpeed - this.lowSpeed) / slowDownRange;
						final double newSpeed = this.maxSpeed - (deceleration * (slowDownRange - distanceToMax));
						this.dartTalon.set(newSpeed);
					}
					else
					{
						// If we have more distance to the maximum than the 15 point limit, slow down
						// the actuator at maximum speed until we need to decelerate
						this.dartTalon.set(this.maxSpeed);
					}
				}
				else
				{
					this.dartTalon.set(0);// stop the talon if the potentiometer value is less than the value for
											// retraction.
					// prevents jamming the actuator
				}
				break;
			case 1:
				if(this.potValue < this.floorValue)
				{ // if the user wants to raise the claw and the claw hasn't hit its max yet
					final double distanceToMax = Math.abs(this.potValue - this.floorValue); // the amount needed to
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
						final double deceleration = (this.maxSpeed - this.lowSpeed) / slowDownRange;
						final double newSpeed = this.maxSpeed - (deceleration * (slowDownRange - distanceToMax));
						this.dartTalon.set(newSpeed);
					}
					else
					{
						// If we have more distance to the maximum than the 15 point limit, slow down
						// the actuator at maximum speed until we need to decelerate
						this.dartTalon.set(this.maxSpeed);
					}
				}
				else
				{
					this.dartTalon.set(0); // stop the talon if the potentiometer value is greater than the value for
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
		this.potValue = this.dartTalon.getSensorCollection().getAnalogIn(); // get the analog value of the talon on
																			// which the Dart Actuator runs
		if(oi.get(OI.Button.CLOSE_CLAW))
		{
			this.grabber.set(DoubleSolenoid.Value.kReverse);
			this.clawClosed = true;
		}
		else if(oi.get(OI.Button.OPEN_CLAW))
		{
			this.grabber.set(DoubleSolenoid.Value.kForward);
			this.clawClosed = false;
		}
		else
		{
			this.grabber.set(DoubleSolenoid.Value.kOff);
		}
		if(oi.get(OI.Button.EXTEND_SHOOTER))
		{
			this.shooter.set(DoubleSolenoid.Value.kForward);
			this.shooterPosition = true;
		}
		else
		{
			this.shooter.set(DoubleSolenoid.Value.kReverse);
			this.shooterPosition = false;
		}
		if(oi.get(OI.Button.INTAKE_CUBE))
		{
			// CHANGE BASED ON NEW GEARBOXES
			this.setWheelSpeed(-0.30);
		}
		else if(oi.get(OI.Button.OUTTAKE_CUBE))
		{
			// CHANGE BASED ON NEW GEARBOXES
			this.setWheelSpeed(0.40);
		}
		else if(oi.get(OI.Button.POWER_SHOOTER))
		{
			// CHANGE BASED ON NEW GEARBOXES
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
