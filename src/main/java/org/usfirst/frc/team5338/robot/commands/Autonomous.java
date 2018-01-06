package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous extends CommandGroup {
    public Autonomous() {
	switch (SmartDashboard.getString("AUTONOMOUS CHOICE", "NO CHOICE MADE")) {
	case "CENTERGEAR":
	    addSequential(new MoveSlowly(4));
	    addSequential(new DepositGear());
	    break;
	case "LEFTGEAR":
	    addSequential(new MoveSlowly(3));
	    addSequential(new Turn(60));
	    addSequential(new MoveSlowly(4));
	    addSequential(new DepositGear());
	    break;
	case "RIGHTGEAR":
	    addSequential(new MoveSlowly(3));
	    addSequential(new Turn(-60));
	    addSequential(new MoveSlowly(4));
	    addSequential(new DepositGear());
	    break;
	case "BASELINE":
	    addSequential(new MoveFast(4));
	    break;
	case "TEST":
	    addSequential(new Turn(60));
	    addSequential(new MoveSlowly(1));
	    addSequential(new Turn(-60));
	    break;
	default:
	    break;
	}
    }
}