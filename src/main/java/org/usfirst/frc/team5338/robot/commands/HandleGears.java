package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class HandleGears extends Command {
    public HandleGears() {
	requires(Robot.gearhandler);
    }

    protected void execute() {
	Robot.gearhandler.handleGears(Robot.oi);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
	Robot.gearhandler.setGears(DoubleSolenoid.Value.kOff);
    }
}