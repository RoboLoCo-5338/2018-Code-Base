package org.usfirst.frc.team5338.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI
{
	public enum Button
	{
		SLOW, STRAIGHT
	}

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

	private final Joystick joyL = new Joystick(0);
	private final Joystick joyR = new Joystick(1);

	public OI()
	{
	}
	public boolean get(final Button button)
	{
		switch(button)
		{
			case SLOW:
				return this.joyL.getRawButton(1);
			case STRAIGHT:
				return this.joyR.getRawButton(1);
			default:
				return false;
		}
	}
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
	public double getLeft()
	{
		return OI.joystickDeadZone(this.joyL.getRawAxis(1));
	}
	public double getRight()
	{
		return OI.joystickDeadZone(this.joyR.getRawAxis(1));
	}
}