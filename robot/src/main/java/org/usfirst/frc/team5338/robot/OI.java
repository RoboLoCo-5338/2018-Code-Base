//Package for all of our code.
package org.usfirst.frc.team5338.robot;

//Import of all essential wpilib classes.
import edu.wpi.first.wpilibj.Joystick;

//Main class that handles Operator Input.
public class OI
{
	// Enum that represents all possible buttons in use.
	public enum Button // Shoot
	{
		SHIFT_UP, SHIFT_DOWN, INTAKE, OUTTAKE, CLOSE, OPEN, EXTEND, RETRACT, FLOOR, SWITCH, SCALE, SPIN_UP, SHOOT, FAST_MODE, SLOW_MODE, SAFE_MODE, ARCADE_MODE
	}
	
	// Private method that returns a deadzone-adjusted value for a joystick value
	// input.
	private static double joystickDeadZone(final double value)
	{
		if(value > 0.075)
		{
			return (value - 0.075) / 0.925;
		}
		else if(value < -0.075)
		{
			return (value + 0.075) / 0.925;
		}
		return value;
	}
	
	// Creates private joysticks objects for use.
	private final Joystick leftJoystick = new Joystick(0);
	private final Joystick rightJoystick = new Joystick(1);
	
	// Public method that returns the state of a particular button based on the
	// Button enum.
	public boolean get(final Button button)
	{
		// TODO CHECK CONTROL SCHEME WORKS
		switch(button)
		{
			case CLOSE:
				return this.rightJoystick.getRawButton(1);
			case OPEN:
				return this.rightJoystick.getRawButton(2);
			case INTAKE:
				return this.rightJoystick.getRawButton(4);
			case OUTTAKE:
				return this.rightJoystick.getRawButton(6);
			case SPIN_UP:
				return this.rightJoystick.getRawButton(5);
			case SHOOT:
				return this.rightJoystick.getRawButton(3);
			case EXTEND:
				return this.rightJoystick.getRawButton(9);
			case RETRACT:
				return this.rightJoystick.getRawButton(10);
			case SHIFT_UP:
				return this.rightJoystick.getRawButton(11);
			case SHIFT_DOWN:
				return this.rightJoystick.getRawButton(12);
			case FLOOR:
				return this.leftJoystick.getRawButton(2);
			case SWITCH:
				return this.leftJoystick.getRawButton(3);
			case SCALE:
				return this.leftJoystick.getRawButton(5);
			case FAST_MODE:
				return this.leftJoystick.getRawButton(9) && this.leftJoystick.getRawButton(10);
			case SLOW_MODE:
				return this.leftJoystick.getRawButton(9) && this.leftJoystick.getRawButton(11);
			case SAFE_MODE:
				return this.leftJoystick.getRawButton(9) && this.leftJoystick.getRawButton(12);
			case ARCADE_MODE:
				return this.leftJoystick.getRawButton(9) && this.leftJoystick.getRawButton(8);
			default:
				return false;
		}
	}
	// Public method that returns the left joystick's deadzone-adjusted values
	public double getLeftJoystick(final char input)
	{
		switch(input)
		{
			case 'X': // Gets deadzone corrected x-axis position
				return OI.joystickDeadZone(this.leftJoystick.getRawAxis(0));
			case 'Y': // Gets deadzone corrected y-axis position
				return -OI.joystickDeadZone(this.leftJoystick.getRawAxis(1));
			case 'Z': // Gets deadzone corrected z-axis (rotation) position
				return OI.joystickDeadZone(this.leftJoystick.getRawAxis(2));
			default: // Returns 0.0 is argument is unknown
				return 0.0;
		}
	}
	// Public method that returns the right joystick's deadzone-adjusted values
	public double getRightJoystick(final char input)
	{
		switch(input)
		{
			case 'X': // Gets deadzone corrected x-axis position
				return OI.joystickDeadZone(this.rightJoystick.getRawAxis(0));
			case 'Y': // Gets deadzone corrected y-axis position
				return -OI.joystickDeadZone(this.rightJoystick.getRawAxis(1));
			case 'Z': // Gets deadzone corrected z-axis (rotation) position
				return OI.joystickDeadZone(this.rightJoystick.getRawAxis(2));
			default: // Returns 0.0 is argument is unknown
				return 0.0;
		}
	}
}
