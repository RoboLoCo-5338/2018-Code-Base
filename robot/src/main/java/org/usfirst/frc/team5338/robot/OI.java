//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.Joystick;

//Main class that handles Operator Input.
public class OI
{
	// Enum that represents all possible buttons in use.
	public enum Button
	{
		SHIFT_UP, SHIFT_DOWN, RAISE_CLAW, LOWER_CLAW, INTAKE, OUTTAKE, CLOSE_CLAW, OPEN_CLAW, OUTTAKE_FULL_POWER,
		EXTEND_CLIMB, RETRACT_CLIMB, TIP_HOOK
	}
	
	// Private method that returns a deadzone-adjusted value for a joystick value
	// input.
	private static double joystickDeadZone(final double value)
	{
		if(value > 0.05)
		{
			return (value - 0.025) / 0.975;
		}
		else if(value < -0.05)
		{
			return (value + 0.025) / 0.975;
		}
		return value;
	}
	
	// Creates private joystick objects for use.
	private final Joystick joystick = new Joystick(0);
	
	// Public method that returns the state of a particular button based on the
	// Button enum.
	public boolean get(final Button button)
	{
		switch(button)
		{
			case SHIFT_DOWN:
				return this.joystick.getRawButton(12);
			case SHIFT_UP:
				return this.joystick.getRawButton(11);
			case LOWER_CLAW:
				return this.joystick.getRawButton(5);
			case RAISE_CLAW:
				return this.joystick.getRawButton(3);
			case OUTTAKE_FULL_POWER:
				return this.joystick.getRawButton(7);
			case OUTTAKE:
				return this.joystick.getRawButton(4);
			case INTAKE:
				return this.joystick.getRawButton(6);
			case CLOSE_CLAW:
				return this.joystick.getRawButton(1);
			case OPEN_CLAW:
				return this.joystick.getRawButton(2);
			case TIP_HOOK:
				return this.joystick.getRawButton(8);
			case EXTEND_CLIMB:
				return this.joystick.getRawButton(10);
			case RETRACT_CLIMB:
				return this.joystick.getRawButton(9);
			default:
				return false;
		}
	}
	// Public method that returns the desired joystick object based on the int
	// input.
	public Joystick getJoystick(final int n)
	{
		if(n == 0)
		{
			return this.joystick;
		}
		else if(n == 1)
		{
			return null;// return this.joyR;
		}
		else
		{
			return null;
		}
	}
	// Public method that returns the joystick's deadzone-adjusted values
	public double getLeft(final char input)
	{
		switch(input)
		{
			case 'X': // Gets deadzone corrected x-axis position
				return OI.joystickDeadZone(this.joystick.getRawAxis(0));
			case 'Y': // Gets deadzone corrected y-axis position
				return -OI.joystickDeadZone(this.joystick.getRawAxis(1));
			case 'Z': // Gets deadzone corrected z-axis (rotation) position
				return OI.joystickDeadZone(this.joystick.getRawAxis(2));
			default: // Returns 0.0 is argument is unknown
				return 0.0;
		}
	}
}
