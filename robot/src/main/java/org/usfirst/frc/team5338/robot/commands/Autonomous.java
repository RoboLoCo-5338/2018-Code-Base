package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	private final String location = "CENTER"; // "LEFT" //"RIGHT"
	private final boolean lineCrossOnly = true;
	private final StraightDrive tenFeetMove = new StraightDrive(120.0);
	
	public Autonomous()
	{
		final MatchData.OwnedSide side = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		if(side == MatchData.OwnedSide.LEFT)
		{
			switch(this.location)
			{
				case "LEFT":
					if(this.lineCrossOnly)
					{
						this.addSequential(this.tenFeetMove);
					}
					else
					{
						this.addSequential(new StraightDrive(168.0));
						this.addSequential(new Turn(90));
						this.addSequential(new StraightDrive(16));
						// Eject cube
					}
					break;
				case "CENTER":
					if(this.lineCrossOnly)
					{
						this.addSequential(this.tenFeetMove);
					}
					else
					{
						this.addSequential(new Turn(-38));
						this.addSequential(new StraightDrive(215.0));
						this.addSequential(new Turn(129));
						// Eject cube
					}
					break;
				case "RIGHT":
					if(this.lineCrossOnly)
					{
						this.addSequential(this.tenFeetMove);
					}
					else
					{
						break;
					}
					break;
				default:
					break;
			}
		}
		else if(side == MatchData.OwnedSide.RIGHT)
		{
			switch(this.location)
			{
				case "LEFT":
					if(this.lineCrossOnly)
					{
						this.addSequential(this.tenFeetMove);
					}
					else
					{
						break;
					}
					break;
				case "CENTER":
					if(this.lineCrossOnly)
					{
						this.addSequential(this.tenFeetMove);
					}
					else
					{
						this.addSequential(new Turn(26));
						this.addSequential(new StraightDrive(189.0));
						this.addSequential(new Turn(-154));
						// Eject cube
					}
					break;
				case "RIGHT":
					if(this.lineCrossOnly)
					{
						this.addSequential(this.tenFeetMove);
					}
					else
					{
						this.addSequential(new StraightDrive(168.0));
						this.addSequential(new Turn(-90));
						this.addSequential(new StraightDrive(16));
						// Eject cube
					}
					break;
				default:
					break;
			}
		}
		else
		{
			this.addSequential(this.tenFeetMove);
		}
	}
	@Override
	protected void initialize()
	{
		this.tenFeetMove.initialize();
	}
}
