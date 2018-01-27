package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5338.robot.Robot;

public class DartActuator extends Command {
    public DartActuator()
    {
        this.requires(Robot.dartActuatorControl);
    }
    @Override
    protected void end()
    {
        //Robot.dartActuatorControl.
    }
    @Override
    protected void execute()
    {
        //Robot.drivetrain.drive(Robot.oi);
        Robot.dartActuatorControl.dartActuator(Robot.oi);
    }
    @Override
    protected boolean isFinished()
    {
        return false;
    }
}
