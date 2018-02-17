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
		SLOW, STRAIGHT, SHIFT_UP, SHIFT_DOWN, RAISE, LOWER
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
	private final Joystick joyL = new Joystick(0);
	private final Joystick joyR = new Joystick(1);
	
	// Public method that returns the state of a particular button based on the
	// Button enum.
	public boolean get(final Button button)
	{
		switch(button)
		{
			case SLOW:
				return this.joyL.getRawButton(1);
			case STRAIGHT:
				return this.joyL.getRawButton(2);
			case SHIFT_DOWN:
				return this.joyL.getRawButton(6);
			case SHIFT_UP:
				return this.joyL.getRawButton(4);
			case LOWER:
				return this.joyL.getRawButton(3);
			case RAISE:
				return this.joyL.getRawButton(5);
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
			return this.joyL;
		}
		else if(n == 1)
		{
			return this.joyR;
		}
		else
		{
			return null;
		}
	}
	// Public method that returns the left joystick's deadzone-adjusted y-axis value
	public double getLeft(final char input)
	{
		switch(input)
		{
			case 'X': // Gets deadzone corrected x-axis position
				return OI.joystickDeadZone(this.joyL.getRawAxis(0));
			case 'Y': // Gets deadzone corrected y-axis position
				return -OI.joystickDeadZone(this.joyL.getRawAxis(1));
			case 'Z': // Gets deadzone corrected z-axis (rotation) position
				return OI.joystickDeadZone(this.joyL.getRawAxis(2));
			default: // Returns 0.0 is argument is unknown
				return 0.0;
		}
	}
	// Public method that returns the right joystick's deadzone-adjusted y-axis
	// value
	public double getRight(final char input)
	{
		switch(input)
		{
			case 'X': // Gets deadzone corrected x-axis position
				return OI.joystickDeadZone(this.joyR.getRawAxis(0));
			case 'Y': // Gets deadzone corrected y-axis position
				return -OI.joystickDeadZone(this.joyR.getRawAxis(1));
			case 'Z': // Gets deadzone corrected z-axis (rotation) position
				return OI.joystickDeadZone(this.joyR.getRawAxis(2));
			default: // Returns 0.0 is argument is unknown
				return 0.0;
		}
	}
}
