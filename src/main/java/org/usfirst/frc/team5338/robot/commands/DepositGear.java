package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class DepositGear extends Command {
    public DepositGear() {
	requires(Robot.drivetrain);
	requires(Robot.gearhandler);
	setTimeout(2);
    }

    protected void execute() {
	Robot.gearhandler.setGears(DoubleSolenoid.Value.kReverse);
	if (timeSinceInitialized() >= 0.75)
	    Robot.drivetrain.drive(0.25, 0.25);
    }

    protected boolean isFinished() {
	return isTimedOut();
    }

    protected void end() {
	Robot.drivetrain.drive(0.0, 0.0);
	Robot.gearhandler.setGears(DoubleSolenoid.Value.kForward);
    }
}