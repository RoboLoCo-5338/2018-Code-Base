package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	private final String autonomous;

	public Autonomous()
	{
		this.autonomous = "TESTING";// Robot.autonomousChooser.getSelected(); //THIS IS TEMPORARY ONLY, PLEASE FIX
									// ME!!
		final MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		final MatchData.OwnedSide scaleSide = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		switch(this.autonomous)
		{
			case "TESTING": // THIS IS TEMPORARY ONLY, PLEASE REMOVE ME!!
				// Tests go here
				break;
			case "BASELINE":
				this.addSequential(new Straight(126.0));
				break;
			case "CENTER":
				if(switchSide == MatchData.OwnedSide.LEFT)
				{
					this.addSequential(new Straight(36.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(75.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(45.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(34.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
					break;
				}
				else if(switchSide == MatchData.OwnedSide.RIGHT)
				{
					this.addSequential(new Straight(36.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(75.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-45.00));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(34.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
					break;
				}
				break;
			case "RIGHTSWITCHSCALE": // Will do scale only if switch unfavorable, will do baseline if both
										// unfavorable
				if(switchSide == MatchData.OwnedSide.RIGHT)
				{
					this.addSequential(new Straight(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				}
				else if(scaleSide == MatchData.OwnedSide.RIGHT)
				{
					this.addSequential(new Straight(260));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new SetClawSpeed(0.99));
					this.addSequential(new Delay(2));
					this.addSequential(new LaunchCube());
					this.addSequential(new SetClawSpeed(0));
				}
				else
				{
					this.addSequential(new Straight(126.0));
				}
				break;
			case "RIGHTSCALESWITCH": // Will do switch only if scale unfavorable, will do baseline if both
										// unfavorable
				if(scaleSide == MatchData.OwnedSide.RIGHT)
				{
					this.addSequential(new Straight(260));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new SetClawSpeed(0.99));
					this.addSequential(new Delay(2));
					this.addSequential(new LaunchCube());
					this.addSequential(new SetClawSpeed(0));
				}
				else if(switchSide == MatchData.OwnedSide.RIGHT)
				{
					this.addSequential(new Straight(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				}
				else
				{
					this.addSequential(new Straight(126.0));
				}
				break;
			case "LEFTSWITCHSCALE": // Will do scale only if switch unfavorable, will do baseline if both
				if(switchSide == MatchData.OwnedSide.LEFT)
				{
					this.addSequential(new Straight(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				}
				else if(scaleSide == MatchData.OwnedSide.LEFT)
				{
					this.addSequential(new Straight(260));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new SetClawSpeed(0.99));
					this.addSequential(new Delay(2));
					this.addSequential(new LaunchCube());
					this.addSequential(new SetClawSpeed(0));
				}
				else
				{
					this.addSequential(new Straight(126.0));
				}
				break;
			case "LEFTSCALESWITCH": // Will do switch only if scale unfavorable, will do baseline if both
				if(scaleSide == MatchData.OwnedSide.LEFT)
				{
					this.addSequential(new Straight(260));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new SetClawSpeed(0.99));
					this.addSequential(new Delay(2));
					this.addSequential(new LaunchCube());
					this.addSequential(new SetClawSpeed(0));
				}
				else if(switchSide == MatchData.OwnedSide.LEFT)
				{
					this.addSequential(new Straight(138.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				}
				else
				{
					this.addSequential(new Straight(126.0));
				}
				break;
			default:
				break;
		}
	}
}
//