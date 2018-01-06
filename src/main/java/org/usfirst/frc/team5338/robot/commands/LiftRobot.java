package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftRobot extends Command {
    public LiftRobot() {
	requires(Robot.wincher);
    }

    @Override
    protected void execute() {
	Robot.wincher.liftRobot(Robot.oi);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	Robot.wincher.stopLift();
    }
}