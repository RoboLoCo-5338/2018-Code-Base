package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	private final String location = "CENTER"; // "LEFT" //"RIGHT"
	private final boolean lineCrossOnly = true;

	public Autonomous()
	{
		final MatchData.OwnedSide side = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		System.out.println(side);
		if(side == MatchData.OwnedSide.LEFT)
		{
			switch(this.location)
			{
				case "LEFT":
					if(this.lineCrossOnly)
					{
						this.addSequential(new MoveForward(120.0));
					}
					else
					{
					}
					break;
				case "CENTER":
					if(this.lineCrossOnly)
					{
						this.addSequential(new MoveForward(120.0));
					}
					else
					{
					}
					break;
				case "RIGHT":
					if(this.lineCrossOnly)
					{
						this.addSequential(new MoveForward(120.0));
					}
					else
					{
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
						this.addSequential(new MoveForward(120.0));
					}
					else
					{
					}
					break;
				case "CENTER":
					if(this.lineCrossOnly)
					{
						this.addSequential(new MoveForward(120.0));
					}
					else
					{
					}
					break;
				case "RIGHT":
					if(this.lineCrossOnly)
					{
						this.addSequential(new MoveForward(120.0));
					}
					else
					{
					}
					break;
				default:
					break;
			}
		}
		else
		{
			this.addSequential(new MoveForward(120.0));
		}
	}
	// @Override
	// protected void initialize()
	// {
	// this.move.initialize();
	// }
}
