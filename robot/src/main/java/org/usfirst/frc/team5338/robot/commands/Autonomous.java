package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import openrio.powerup.MatchData;

public class Autonomous extends CommandGroup
{
	private final String autonomous;

	public Autonomous()
	{
		this.autonomous = Robot.autonomousChooser.getSelected();
		final MatchData.OwnedSide switchSide = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		final MatchData.OwnedSide scaleSide = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		switch(this.autonomous)
		{
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
				// Make sure to reuse code that we know works
			case "RIGHTSWITCHSCALE": // Will do scale only if switch unfavorable, will do baseline if both
				if(switchSide == MatchData.OwnedSide.RIGHT) {
					this.addSequential(new Straight(138.0))
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				} else if(scaleSide == MatchData.OwnedSide.RIGHT) {
					this.addSequential(new SpinWheels(-0.30, 0.5));
					this.addSequential(new ResetSensors());
					this.addSequential(new Staright(251.8));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(3));
					this.addSequential(new SpinWheels(0.99, 4.0));
					this.addSequential(new Shoot());
				} else {
					this.addSequential(new Straight(126.0));
				}

				break;
			case "RIGHTSCALESWITCH": // Will do switch only if scale unfavorable, will do baseline if both
				if(scaleSide == MatchData.OwnedSide.RIGHT) {
					this.addSequential(new SpinWheels(-0.30, 0.5));
					this.addSequential(new ResetSensors());
					this.addSequential(new Staright(251.8));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(3));
					this.addSequential(new SpinWheels(0.99, 4.0));
					this.addSequential(new Shoot());
				} else if(switchSide == MatchData.OwnedSide.RIGHT) {
					this.addSequential(new Straight(138.0))
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(-80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				} else {
					this.addSequential(new Straight(126.0));
				}

				break;
			case "LEFTSWITCHSCALE": // Will do scale only if switch unfavorable, will do baseline if both
				if(switchSide == MatchData.OwnedSide.LEFT) {
					this.addSequential(new Straight(138.0))
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				} else if(scaleSide == MatchData.OwnedSide.LEFT) {
					this.addSequential(new SpinWheels(-0.30, 0.5));
					this.addSequential(new ResetSensors());
					this.addSequential(new Staright(251.8));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(3));
					this.addSequential(new SpinWheels(0.99, 4.0));
					this.addSequential(new Shoot());
				} else {
					this.addSequential(new Straight(126.0));
				}

				break;
			case "LEFTSCALESWITCH": // Will do switch only if scale unfavorable, will do baseline if both
				if(scaleSide == MatchData.OwnedSide.LEFT) {
					this.addSequential(new SpinWheels(-0.30, 0.5));
					this.addSequential(new ResetSensors());
					this.addSequential(new Staright(251.8));
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(-40.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new ChangeClawPosition(3));
					this.addSequential(new SpinWheels(0.99, 4.0));
					this.addSequential(new Shoot());
				} else if(switchSide == MatchData.OwnedSide.LEFT) {
					this.addSequential(new Straight(138.0))
					this.addSequential(new ResetSensors());
					this.addSequential(new Turn(80.0));
					this.addSequential(new ResetSensors());
					this.addSequential(new Straight(40.0));
					this.addSequential(new ChangeClawPosition(2));
					this.addSequential(new DepositCube(0.25));
				} else {
					this.addSequential(new Straight(126.0));
				}
				break;
			default:
				break;
		}
		// if(switchSide == MatchData.OwnedSide.LEFT)
		// {
		// switch(this.autonomous)
		// {
		// case "LEFTSWITCHSCALE":
		// this.addSequential(new Straight(138.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Turn(80.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Straight(40.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new ChangeClawPosition(2));
		// this.addSequential(new DepositCube(0.25));
		// break;
		// }
		// }
		// else if(switchSide == MatchData.OwnedSide.RIGHT)
		// {
		// switch(this.autonomous)
		// {
		// case "RIGHTSWITCHSCALE":
		// this.addSequential(new Straight(138.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Turn(-80.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Straight(40.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new ChangeClawPosition(2));
		// this.addSequential(new DepositCube(0.25));
		// break;
		// case "BASELINE":
		// this.addSequential(new Straight(130.0));
		// break;
		// default:
		// break;
		// }
		// }
		// if(scaleSide == MatchData.OwnedSide.LEFT)
		// {
		// switch(this.autonomous)
		// {
		// case "LEFTSCALESWITCH":
		// this.addSequential(new Straight(275.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Turn(80.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Straight(-30.0));
		// this.addSequential(new ResetSensors());
		// // this.addSequential(new ShootCube(0.99));
		// // this.addSequential(new Delay(2.5));
		// break;
		// default:
		// break;
		// }
		// }
		// else if(scaleSide == MatchData.OwnedSide.RIGHT)
		// {
		// switch(this.autonomous)
		// {
		// case "RIGHTSCALESWITCH":
		// this.addSequential(new Straight(275.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Turn(-80.0));
		// this.addSequential(new ResetSensors());
		// this.addSequential(new Straight(-30.0));
		// this.addSequential(new ResetSensors());
		// break;
		// default:
		// break;
		// }
		// }
	}
}
//