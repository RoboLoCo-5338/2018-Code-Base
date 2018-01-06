package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn extends PIDCommand {
    public Turn(int angle) {
	// super(0.001*.2, .64/2, .64/3, 0.01); //anything higher than .005 is
	// essentially the same
	super(0.0057, 0.0000120, 0, 0.005);
	requires(Robot.drivetrain);
	getPIDController().setOutputRange(-0.425, 0.425);
	getPIDController().setInputRange(-180.0, 180.0);
	getPIDController().setContinuous();
	double targetHeading = ((double) (-Robot.ahrs.getYaw()) + angle);
	if (targetHeading < -180) {
	    setSetpoint(180.0 + targetHeading % 180);
	} else if (targetHeading > 180) {
	    setSetpoint(-180 + targetHeading % 180);
	} else {
	    setSetpoint(targetHeading);
	}
	setTimeout(5);
    }

    protected void execute() {
	SmartDashboard.putNumber("CURRENT HEADING", Robot.ahrs.getYaw());
    }

    protected boolean isFinished() {
	return isTimedOut();
    }

    protected void end() {
	Robot.drivetrain.drive(0.0, 0.0);
    }

    protected double returnPIDInput() {
	return -Robot.ahrs.getYaw();
    }

    protected void usePIDOutput(double output) {
	Robot.drivetrain.drive(-output, output);
    }
}
