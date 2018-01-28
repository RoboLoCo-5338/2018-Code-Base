package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderCommand extends Command
{
    public EncoderCommand()
    {
        this.requires(Robot.encoders);
    }
    @Override
    protected void end()
    {
        Robot.encoders.returnRotation(Robot.oi);
    }
    @Override
    protected void execute()
    {
        Robot.encoders.returnRotation(Robot.oi);
    }
    @Override
    protected boolean isFinished()
    {
        return false;
    }
}
