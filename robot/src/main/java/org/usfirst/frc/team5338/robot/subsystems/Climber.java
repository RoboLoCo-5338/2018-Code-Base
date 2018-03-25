package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.ClimberControl;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem
{
	private final DoubleSolenoid CLIMBER = new DoubleSolenoid(8, 1, 2);
	private boolean climberExtended = false;
	
	@Override
	protected void initDefaultCommand()
	{
		this.setDefaultCommand(new ClimberControl());
	}
	public Climber()
	{
		super();
	}
	public void control(final OI oi)
	{
		if(oi.get(OI.Button.EXTEND_CLIMB))
		{
			this.CLIMBER.set(DoubleSolenoid.Value.kReverse);
			this.climberExtended = true;
		}
		else if(oi.get(OI.Button.RETRACT_CLIMB))
		{
			this.CLIMBER.set(DoubleSolenoid.Value.kForward);
			this.climberExtended = false;
		}
		// log the status
		SmartDashboard.putBoolean("Climber Status", this.climberExtended);
	}
}
