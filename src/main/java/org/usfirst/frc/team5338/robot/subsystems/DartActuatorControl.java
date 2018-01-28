package org.usfirst.frc.team5338.robot.subsystems;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.DartActuator;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;


public class DartActuatorControl extends Subsystem {

    private final WPI_TalonSRX dartTalon = new WPI_TalonSRX(31);
    private double potValue;
    final double maxlift = 831;
    final double minlowerd = 896;
    public DartActuatorControl() {
        super();
        //dartTalon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);

    }

    //
    public void initDefaultCommand() {
        this.setDefaultCommand(new DartActuator());
    }

    public void dartActuator(final OI oi) {

        potValue = dartTalon.getSensorCollection().getAnalogIn();
        if (Robot.oi.get(OI.Button.RAISE)) {
            if (potValue <= minlowerd) {
                double difference = Math.abs(potValue - minlowerd);
                int distance = 12;
                if (difference <= distance) {
                    double speedDifference = (0.90 - 0.10) / distance;
                    dartTalon.set(0.90 - (speedDifference * (distance - difference)));
                }
                else
                    dartTalon.set(0.90);

            }
            else {
                dartTalon.set(0);
            }
        } else if (Robot.oi.get(OI.Button.LOWER)) {
            if (potValue >= maxlift) {
                double difference = Math.abs(potValue - maxlift);
                int distance = 20;
                if (difference <= distance) {
                    double speedDifference = (0.90 - 0.10) / distance;
                    dartTalon.set(-(0.90 - (speedDifference * (distance - difference))));
                }
                else
                    dartTalon.set(-0.90);
            }
            else {
                dartTalon.set(0);
            }
        } else {
            dartTalon.set(0);
        }
        SmartDashboard.putNumber("pot", potValue);
    }
}
