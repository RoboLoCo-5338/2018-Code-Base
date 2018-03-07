package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	// private final StraightDrive eleven = new StraightDrive(138.0);
	// private final StraightDrive twoFeet = new StraightDrive(24.0);
	private final String location = "LEFT"; // "RIGHT" "CENTER"

	public Autonomous()
	{
		final MatchData.OwnedSide side = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		if(side == MatchData.OwnedSide.LEFT)
		{
			switch(this.location)
			{
				case "LEFT":
					this.addSequential(new StraightDrive(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new StraightDrive(24.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new EjectCube());
					break;
				case "CENTER":
					this.addSequential(new StraightDrive(36.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-46.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new StraightDrive(108.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(46));
					this.addSequential(new ResetSensors());
					this.addSequential(new StraightDrive(36.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new EjectCube());
					break;
				default:
					this.addSequential(new StraightDrive(138.0));
					break;
			}
		}
		else if(side == MatchData.OwnedSide.RIGHT)
		{
			switch(this.location)
			{
				case "RIGHT":
					this.addSequential(new StraightDrive(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new StraightDrive(24.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new EjectCube());
					break;
				case "CENTER":
					this.addSequential(new StraightDrive(36.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new StraightDrive(108.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new StraightDrive(36.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new EjectCube());
					break;
				default:
					this.addSequential(new StraightDrive(138.0));
					break;
			}
		}
	}
}
