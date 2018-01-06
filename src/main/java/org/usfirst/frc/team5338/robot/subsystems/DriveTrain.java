package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.OI.DriveState;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
    private final CANTalon DRIVEL1 = new CANTalon(4);
    public final CANTalon DRIVEL2 = new CANTalon(3);
    public final CANTalon DRIVER1 = new CANTalon(2);
    public final CANTalon DRIVER2 = new CANTalon(1);

    public final RobotDrive DRIVE = new RobotDrive(DRIVEL1, DRIVEL2, DRIVER1, DRIVER2);

    private double throttle = 1.0;

    public DriveTrain() {
	super();
    }

    @Override
    public void initDefaultCommand() {
	setDefaultCommand(new TankDriveWithJoysticks());
    }

    public void drive(OI oi) {
	if (oi.get(OI.Button.REVERSE)) {
	    oi.driveState = DriveState.REVERSE;
	} else if (oi.get(OI.Button.FORWARD)) {
	    oi.driveState = DriveState.FORWARD;
	}

	if (oi.get(OI.Button.SLOW)) {
	    throttle = 0.5;
	} else {
	    throttle = 1.0;
	}

	switch (oi.driveState) {
	case REVERSE:
	    if (oi.get(OI.Button.STRAIGHT)) {
		DRIVE.tankDrive(oi.getLeft(), oi.getLeft(), false);
	    } else {
		DRIVE.tankDrive(throttle * oi.getRight(), throttle * oi.getLeft(), false);
	    }
	    break;
	case FORWARD:
	    if (oi.get(OI.Button.STRAIGHT)) {
		DRIVE.tankDrive(-oi.getLeft(), -oi.getLeft(), false);
	    } else {
		DRIVE.tankDrive(-throttle * oi.getLeft(), -throttle * oi.getRight(), false);
	    }
	    break;
	default:
	    drive(0.0, 0.0);
	    break;
	}
    }

    public void drive(double left, double right) {
	DRIVE.tankDrive(throttle * left, throttle * right, false);
    }
}
