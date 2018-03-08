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
		SHIFT_UP, SHIFT_DOWN, INTAKE_CUBE, OUTTAKE_CUBE, CLOSE_CLAW, OPEN_CLAW, SHOOT_CUBE, EXTEND_CLIMB, RETRACT_CLIMB,
		DART_FLOOR, DART_SWITCH, DART_SCALE
	}

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

	// Creates private joystick objects for use.
	private final Joystick leftJoystick = new Joystick(0);
	private final Joystick rightJoystick = new Joystick(1);

	// Public method that returns the state of a particular button based on the
	// Button enum.
	public boolean get(final Button button)
	{
		switch(button)
		{
			case CLOSE_CLAW:
				return this.rightJoystick.getRawButton(1);
			case OPEN_CLAW:
				return this.rightJoystick.getRawButton(2);
			case INTAKE_CUBE:
				return this.rightJoystick.getRawButton(4);
			case OUTTAKE_CUBE:
				return this.rightJoystick.getRawButton(6);
			case SHOOT_CUBE:
				return this.rightJoystick.getRawButton(5);
			case EXTEND_CLIMB:
				return this.rightJoystick.getRawButton(9);
			case RETRACT_CLIMB:
				return this.rightJoystick.getRawButton(10);
			case SHIFT_UP:
				return this.rightJoystick.getRawButton(11);
			case SHIFT_DOWN:
				return this.rightJoystick.getRawButton(12);
			case DART_FLOOR:
				return this.leftJoystick.getRawButton(2);
			case DART_SWITCH:
				return this.leftJoystick.getRawButton(3);
			case DART_SCALE:
				return this.leftJoystick.getRawButton(5);
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
