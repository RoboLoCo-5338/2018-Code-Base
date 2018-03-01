package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.ControlClaw;

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
	private final DoubleSolenoid bimba = new DoubleSolenoid(8, 1, 2);
	private final DoubleSolenoid tipper = new DoubleSolenoid(8, 0, 7);
	private double potValue; // potentiometer value
	final double retractedValue = 45; // potentiometer value when the dart actuator is retracted
	final double extendedValue = 705; // potentiometer value when dart actuator is extended
	private boolean hookTipped = false;
	private boolean climberExtended = false;
	private boolean clawOpen = false;
	
	@Override
	protected void initDefaultCommand()
	{ // default command required by Subsystem class. Not being modified
		this.setDefaultCommand(new ControlClaw());
	}
	public Claw()
	{
		super();
	}// Default Constructor
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
			this.clawOpen = false;
		}
		else if(oi.get(OI.Button.OPEN_CLAW))
		{
			this.grabber.set(DoubleSolenoid.Value.kForward);
			this.clawOpen = true;
		}
		else
		{
			this.grabber.set(DoubleSolenoid.Value.kOff);
		}
		if(oi.get(OI.Button.TIP_HOOK))
		{
			this.tipper.set(DoubleSolenoid.Value.kReverse);
			this.hookTipped = true;
		}
		else
		{
			this.tipper.set(DoubleSolenoid.Value.kForward);
		}
		if(oi.get(OI.Button.EXTEND_CLIMB))
		{
			this.bimba.set(DoubleSolenoid.Value.kReverse);
			this.climberExtended = true;
		}
		else if(oi.get(OI.Button.RETRACT_CLIMB))
		{
			this.bimba.set(DoubleSolenoid.Value.kForward);
			this.tipper.set(DoubleSolenoid.Value.kForward);
			this.climberExtended = false;
		}
		if(oi.get(OI.Button.OUTTAKE))
		{
			this.leftMotor.set(-0.50);
			this.rightMotor.set(0.50);
		}
		else if(oi.get(OI.Button.OUTTAKE_FULL_POWER))
		{
			this.leftMotor.set(0.99);
			this.rightMotor.set(-0.99);
		}
		else if(oi.get(OI.Button.INTAKE))
		{
			this.leftMotor.set(0.50);
			this.rightMotor.set(-0.50);
		}
		else
		{
			this.leftMotor.set(0);
			this.rightMotor.set(0);
		}
		if(oi.get(OI.Button.RAISE_CLAW))
		{
			if(this.potValue < this.extendedValue)
			{ // if the user wants to raise the claw and the claw hasn't hit its max yet
				final double distanceToMax = Math.abs(this.potValue - this.extendedValue); // the amount needed to
																							// extend to max
				final int slowDownRange = 150; // declares that actuator will slow 150 points away from the actuator's
												// maximum value
				final double minSpeed = 0.125; // Speed to which the actuator will slow
				final double maxSpeed = 0.99;
				if(distanceToMax <= slowDownRange)
				{
					/**
					 * model the slowing down of the actuator as y = mx + b if we are within
					 * slow-down range
					 **/
					final double deceleration = (maxSpeed - minSpeed) / slowDownRange;
					final double newSpeed = maxSpeed - (deceleration * (slowDownRange - distanceToMax));
					this.dartTalon.set(newSpeed);
				}
				else
				{
					// If we have more distance to the maximum than the 15 point limit, slow down
					// the actuator at maximum speed until we need to decelerate
					this.dartTalon.set(maxSpeed);
				}
			}
			else
			{
				this.dartTalon.set(0); // stop the talon if the potentiometer value is greater than the value for
										// extension.
				// prevents jamming the actuator
			}
		}
		else if(oi.get(OI.Button.LOWER_CLAW))
		{
			if(this.potValue > this.retractedValue)
			{ // if the user wants to retract and the claw hasn't hit minimum value
				final double distanceToMin = Math.abs(this.potValue - this.retractedValue);
				final int slowDownRange = 270; // declares that the actuator will slow 270 points away from minimum
												// value
				final double lowSpeed = 0.125; // speed to which actuator slows
				final double maxSpeed = 0.99;
				if(distanceToMin <= slowDownRange)
				{
					/**
					 * model the slowing down of the actuator as y = mx + b if we are within
					 * slow-down range
					 **/
					final double deceleration = (maxSpeed - lowSpeed) / slowDownRange; // deceleration rate as
																						// calculated in raising portion
																						// of code
					// calculate new speed as was done in raising portion of code, but multiply by
					// -1 to show direction change to lowering
					final double newSpeed = (maxSpeed - (deceleration * (slowDownRange - distanceToMin))) * -1;
					this.dartTalon.set(newSpeed);
				}
				else
				{
					// if we are beyond slow-down range, slow down at max speed
					this.dartTalon.set(-1 * maxSpeed);
				}
			}
			else
			{
				this.dartTalon.set(0);// stop the talon if the potentiometer value is less than the value for
										// retraction.
				// prevents jamming the actuator
			}
		}
		else
		{
			// if the user doesn't want to raise or lower, stop the talon
			this.dartTalon.set(0);
		}
		// log the potentiometer value for testing purposes
		SmartDashboard.putNumber("Potentiometer Position", this.potValue);
		SmartDashboard.putBoolean("Claw Status", this.clawOpen);
		SmartDashboard.putBoolean("Hook Status", this.hookTipped);
		SmartDashboard.putBoolean("Climber Status", this.climberExtended);
	}
}
