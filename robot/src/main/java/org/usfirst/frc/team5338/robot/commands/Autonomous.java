package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	private final String location;
	
	public Autonomous()
	{
		this.location = Robot.autonomousChooser.getSelected();// "CENTER"; // "RIGHT" "CENTER" input;
		final MatchData.OwnedSide side = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		if(side == MatchData.OwnedSide.LEFT)
		{
			switch(this.location)
			{
				case "LEFT":
					this.addSequential(new Straight(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(24.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new HandleCube(0.425));
					break;
				case "CENTER":
					this.addSequential(new Straight(36.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(75.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(35.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(34.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new HandleCube(0.425));
					break;
				default:
					this.addSequential(new Straight(138.0));
					break;
			}
		}
		else if(side == MatchData.OwnedSide.RIGHT)
		{
			switch(this.location)
			{
				case "RIGHT":
					this.addSequential(new Straight(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(24.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new HandleCube(0.425));
					break;
				case "CENTER":
					this.addSequential(new Straight(36.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(75.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-35.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(34.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new HandleCube(0.425));
					break;
				default:
					this.addSequential(new Straight(138.0));
					break;
			}
		}
	}
}
//