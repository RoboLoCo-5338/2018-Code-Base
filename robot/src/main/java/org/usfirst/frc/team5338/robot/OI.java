//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.Joystick;

//Main class that handles Operator Input.
public class OI
{
	// Creates private joystick objects for use, with axis based on Logitech Extreme
	// 3D Pro Joystick only.
	private final Joystick joyL = new Joystick(0);
	private final Joystick joyR = new Joystick(1);
	
	// Private method that returns a deadzone-adjusted value for a joystick value
	// input.
	private static double joystickDeadZone(final double value)
	{
		if(value > 0.025)
		{
			return (value - 0.025) / 0.975;
		}
		else if(value < -0.025)
		{
			return (value + 0.025) / 0.975;
		}
		return value;
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
	// Public method that returns the left joystick's deadzone-adjusted throttle
	// value
	public double getLeftThrottle()
	{
		return OI.joystickDeadZone(this.joyL.getRawAxis(3));
	}
	// Public method that returns the left joystick's deadzone-adjusted y-axis value
	public double getLeftY()
	{
		return OI.joystickDeadZone(this.joyL.getRawAxis(1));
	}
	// Public method that returns the right joystick's deadzone-adjusted y-axis
	// value
	public double getRightY()
	{
		return OI.joystickDeadZone(this.joyR.getRawAxis(1));
	}
}