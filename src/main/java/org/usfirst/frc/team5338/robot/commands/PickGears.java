package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class PickGears extends Command {
    public PickGears() {
	requires(Robot.gearpicker);
    }

    protected void execute() {
	Robot.gearpicker.pickGears(Robot.oi);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
	Robot.gearpicker.setGears(DoubleSolenoid.Value.kOff);
    }
}