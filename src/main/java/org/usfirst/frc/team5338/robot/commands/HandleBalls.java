package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HandleBalls extends Command {
    public HandleBalls() {
	requires(Robot.ballhandler);
    }

    protected void execute() {
	Robot.ballhandler.handleBalls(Robot.oi);
    }

    protected boolean isFinished() {
	return false; // Runs until interrupted
    }

    protected void end() {
	Robot.ballhandler.stopBalls();
    }
}