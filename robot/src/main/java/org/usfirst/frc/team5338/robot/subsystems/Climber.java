package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.ClimberControl;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem
{
	private final DoubleSolenoid bimba = new DoubleSolenoid(8, 1, 2);
	private boolean climberExtended = false;
	
	@Override
	protected void initDefaultCommand()
	{ // default command required by Subsystem class. Not being modified
		this.setDefaultCommand(new ClimberControl());
	}
	public Climber()
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
		if(oi.get(OI.Button.EXTEND_CLIMB))
		{
			this.bimba.set(DoubleSolenoid.Value.kReverse);
			this.climberExtended = true;
		}
		else if(oi.get(OI.Button.RETRACT_CLIMB))
		{
			this.bimba.set(DoubleSolenoid.Value.kForward);
			this.climberExtended = false;
		}
		// log the status
		SmartDashboard.putBoolean("Climber Status", this.climberExtended);
	}
}
