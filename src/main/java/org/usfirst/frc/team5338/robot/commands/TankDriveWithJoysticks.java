package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDriveWithJoysticks extends Command {
    public TankDriveWithJoysticks() {
	requires(Robot.drivetrain);
    }

    @Override
    protected void execute() {
	SmartDashboard.putNumber("CURRENT HEADING", Robot.ahrs.getFusedHeading());
	Robot.drivetrain.drive(Robot.oi);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	Robot.drivetrain.drive(0.0, 0.0);
    }
}