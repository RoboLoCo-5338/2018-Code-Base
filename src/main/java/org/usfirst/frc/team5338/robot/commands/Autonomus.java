package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomus extends CommandGroup {

    public Autonomus() {
        addSequential(new Move(24.0));
    }
}
