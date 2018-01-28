package org.usfirst.frc.team5338.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.EncoderCommand;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

public class EncoderCommandController extends Subsystem {
    private double encoderValue;
    private double encoderLeft, encoderRight = 0.0;
    private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
    public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);

    public EncoderCommandController() {
        super();
    }

    public void returnRotation(final OI oi) {
        encoderLeft = DRIVEL1.getSensorCollection().getQuadraturePosition();
        encoderRight = DRIVER2.getSensorCollection().getQuadraturePosition();

        SmartDashboard.putNumber("encoder left", encoderLeft);
        SmartDashboard.putNumber("encoder right", encoderRight);

    }

    public double measureEncoder(WPI_TalonSRX encoderTalon) {
        encoderValue = encoderTalon.getSensorCollection().getQuadraturePosition();
        return encoderValue;
    }

    @Override
    public void initDefaultCommand() {
        this.setDefaultCommand(new EncoderCommand());
    }
}











