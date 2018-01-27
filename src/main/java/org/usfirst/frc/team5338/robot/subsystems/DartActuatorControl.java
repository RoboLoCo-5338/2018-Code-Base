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
        SmartDashboard.putNumber("potentiometer value", potValue);

    }
}
