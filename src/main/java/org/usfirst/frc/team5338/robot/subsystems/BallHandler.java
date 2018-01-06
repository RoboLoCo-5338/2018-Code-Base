package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.OI.BallState;
import org.usfirst.frc.team5338.robot.commands.HandleBalls;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class BallHandler extends Subsystem {
    private final CANTalon BOTTOM = new CANTalon(5);
    private final CANTalon TOP = new CANTalon(6);

    public BallHandler() {
	super();
	TOP.enable();
	BOTTOM.enable();
    }

    public void initDefaultCommand() {
	setDefaultCommand(new HandleBalls());
    }

    public void handleBalls(OI oi) {
	if (oi.get(OI.Button.OUTTAKE)) {
	    oi.ballState = BallState.OUTTAKE;
	} else if (oi.get(OI.Button.UPPER_INTAKE)) {
	    oi.ballState = BallState.UPPER_INTAKE;
	} else if (oi.get(OI.Button.OFF)) {
	    oi.ballState = BallState.OFF;
	}
	switch (oi.ballState) {
	case OUTTAKE:
	    TOP.set(0.75);
	    BOTTOM.set(0.75);
	    break;
	case UPPER_INTAKE:
	    TOP.set(0.75);
	    BOTTOM.set(-0.75);
	    break;
	default:
	    stopBalls();
	}
    }

    public void stopBalls() {
	TOP.set(0.0);
	BOTTOM.set(0.0);
    }
}
