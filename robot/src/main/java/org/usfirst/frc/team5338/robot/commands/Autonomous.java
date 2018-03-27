package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	private final String autonomous;

	// TODO CHECK EVERYTHING
	// TODO CHECK EVERYTHING AGAIN
	// TODO CHECK EVERYTHING ONE MORE TIME
	public Autonomous()
	{
		this.autonomous = Robot.autonomousChooser.getSelected();
		try
		{
			final MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
			final MatchData.OwnedSide scaleSide = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
			switch(this.autonomous)
			{
				case "TESTING": // THIS IS TEMPORARY ONLY, PLEASE REMOVE ME!!
					// Tests go here
					break;
				case "NOTHING":
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
						this.addSequential(new DepositCube(0.20));
						this.addSequential(new ChangeGear(true));
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
						this.addSequential(new DepositCube(0.20));
						this.addSequential(new ChangeGear(true));
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
						this.addSequential(new DepositCube(0.20));
						this.addSequential(new ChangeGear(true));
					}
					else if(scaleSide == MatchData.OwnedSide.RIGHT)
					{
						this.addSequential(new Straight(260));
						this.addSequential(new ResetSensors());
						this.addSequential(new Turn(-80.0));
						this.addSequential(new ResetSensors());
						this.addSequential(new Straight(-40.0));
						this.addSequential(new SetClawSpeed(0.99));
						this.addSequential(new Delay(1.5));
						this.addSequential(new LaunchCube());
						this.addSequential(new SetClawSpeed(0));
						this.addSequential(new ChangeGear(true));
					}
					else
					{
						this.addSequential(new Straight(126.0));
						this.addSequential(new ChangeGear(true));
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
						this.addSequential(new Delay(1.5));
						this.addSequential(new LaunchCube());
						this.addSequential(new SetClawSpeed(0));
						this.addSequential(new ChangeGear(true));
					}
					else if(switchSide == MatchData.OwnedSide.RIGHT)
					{
						this.addSequential(new Straight(138.0));
						this.addSequential(new ResetSensors());
						this.addSequential(new Turn(-80.0));
						this.addSequential(new ResetSensors());
						this.addSequential(new Straight(40.0));
						this.addSequential(new ChangeClawPosition(2));
						this.addSequential(new DepositCube(0.20));
						this.addSequential(new ChangeGear(true));
					}
					else
					{
						this.addSequential(new Straight(126.0));
						this.addSequential(new ChangeGear(true));
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
						this.addSequential(new DepositCube(0.20));
						this.addSequential(new ChangeGear(true));
					}
					else if(scaleSide == MatchData.OwnedSide.LEFT)
					{
						this.addSequential(new Straight(260));
						this.addSequential(new ResetSensors());
						this.addSequential(new Turn(80.0));
						this.addSequential(new ResetSensors());
						this.addSequential(new Straight(-40.0));
						this.addSequential(new SetClawSpeed(0.99));
						this.addSequential(new Delay(1.5));
						this.addSequential(new LaunchCube());
						this.addSequential(new SetClawSpeed(0));
						this.addSequential(new ChangeGear(true));
					}
					else
					{
						this.addSequential(new Straight(126.0));
						this.addSequential(new ChangeGear(true));
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
						this.addSequential(new Delay(1.5));
						this.addSequential(new LaunchCube());
						this.addSequential(new SetClawSpeed(0));
						this.addSequential(new ChangeGear(true));
					}
					else if(switchSide == MatchData.OwnedSide.LEFT)
					{
						this.addSequential(new Straight(138.0));
						this.addSequential(new ResetSensors());
						this.addSequential(new Turn(80.0));
						this.addSequential(new ResetSensors());
						this.addSequential(new Straight(40.0));
						this.addSequential(new ChangeClawPosition(2));
						this.addSequential(new DepositCube(0.20));
						this.addSequential(new ChangeGear(true));
					}
					else
					{
						this.addSequential(new Straight(126.0));
						this.addSequential(new ChangeGear(true));
					}
					break;
				default:
					this.addSequential(new Straight(126.0));
					this.addSequential(new ChangeGear(true));
					break;
			}
		}
		catch(final Exception e) // The FMS may screw up
		{
			this.addSequential(new Straight(126.0));
			this.addSequential(new ChangeGear(true));
		}
	}
}