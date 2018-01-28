package org.usfirst.frc.team5338.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Move extends Command {
    double distance, encoderLeft, encoderRight, encoderRightBegin, encoderLeftBegin, numRotations, initial;
    private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
    public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);

    public Move(double input) {
        super();
        distance = input;
        requires(Robot.drivetrain);
        requires(Robot.encoders);
        Robot.drivetrain.setShift(DoubleSolenoid.Value.kForward);
        encoderLeftBegin = Robot.encoders.measureEncoder(DRIVEL1);
        encoderRightBegin = Robot.encoders.measureEncoder(DRIVER2);
        numRotations = (distance)/(12*Math.PI);
        initial = Robot.ahrs.getYaw();
    }


    protected void execute() {
        SmartDashboard.putNumber("distanceFinal", distance);

        encoderLeft = Robot.encoders.measureEncoder(DRIVEL1);
        encoderRight = Robot.encoders.measureEncoder(DRIVER2);

        if (encoderLeft < encoderLeftBegin + (numRotations * 4096)) {
            double difference = Math.abs(encoderLeft - (encoderLeftBegin + (numRotations * 4096)));
            double distance1 = 100;
            if (difference <= distance1) {
                double speedDifference = (0.25 - 0.1) / distance1;
                Robot.drivetrain.drive(0.25 - (speedDifference * (distance1 - difference)), 0);
            }
            else
                Robot.drivetrain.drive(0.25, 0);

        }
        else {
            Robot.drivetrain.drive(0, 0);
        }
//

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.drive(0.0,0.0);
    }
}

