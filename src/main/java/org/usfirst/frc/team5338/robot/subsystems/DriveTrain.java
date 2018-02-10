package org.usfirst.frc.team5338.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

//Class in which Robot calls to perform all functions: specifically Drive Train
public class DriveTrain extends Subsystem {

    //Field variables that we will use

    //Talons: motor controllers that we use on the robot
    private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
    public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(4);
    public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(3);
    public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);

    //Objects used to control driving
    private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
    private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
    private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);
    private double throttle = 1.0;
    private double speedPrimeLeft, turn, a;
    int directionRight, directionLeft;

    //Objects that control the shift and compressor mechanism
    private final Compressor driveCompressor = new Compressor(5);
    private final DoubleSolenoid driveSolenoid = new DoubleSolenoid(5, 0, 1);
    private boolean shift;
    private final PowerDistributionPanel pdp = new PowerDistributionPanel(0);

    //Use constructor for any pre-start initialization
    public DriveTrain() {
        super();
        driveCompressor.setClosedLoopControl(true);
        driveCompressor.start();
        driveSolenoid.set(DoubleSolenoid.Value.kForward);
        shift = false;
    }

    //Uses arcade drive, currently deprecated
    @Deprecated
    public void drive(final double front, final double rotate) {
        this.DRIVE.arcadeDrive(this.throttle * front, this.throttle * rotate, false);
    }

    //Actual drive method called in Robot class
    public void drive(final OI oi) {
        //Speed cap
        a = 0.5;

        //Conditionals that changes throttle and direction based on Joystick Input
        if ((this.throttle * Robot.oi.getRight('X')) < 0) {
            directionRight = 1;
        } else {
            directionRight = -1;
        }

        if ((this.throttle * Robot.oi.getLeft('Y')) < 0) {
            directionLeft = 1;
        } else {
            directionLeft = -1;
        }

        if (Robot.oi.get(OI.Button.SLOW)) {
            this.throttle = 0.5;
        } else {
            this.throttle = 1.0;
        }
      
        //Uses directions and input to create a turn coefficient
        turn = Robot.oi.getLeft('X') * Math.abs(Robot.oi.getLeft('X'));
        speedPrimeLeft = -directionLeft * (a * Math.pow(Robot.oi.getLeft('Y'), 3) * (1 - a) * (Robot.oi.getLeft('Y')));

        //Unless the robot is driving straight, use curvature drive if throttle >= 0.6 else use arcade drive
        if (!Robot.oi.get(OI.Button.STRAIGHT)) {
            if (Robot.oi.getLeft('Y') >= 0.6) {
                this.DRIVE.curvatureDrive(speedPrimeLeft, turn, false);
            } else {
                this.DRIVE.arcadeDrive(speedPrimeLeft, turn, false);
            }
        } else {
            this.DRIVE.arcadeDrive(speedPrimeLeft, Robot.oi.getLeft('X'), false);
        }

        //Deprecated drive
        //drive(Robot.oi.getLeft('Y'), Robot.oi.getLeft('Z'));

        //Shift-control system
        if (shift) turn *= .7;

        //Logic to make sure that you can't shift down unless speed is less than 20%
        if (Robot.oi.get(OI.Button.SHIFT_UP)) {
            driveSolenoid.set(DoubleSolenoid.Value.kReverse);
            shift = true;
        } else if (Robot.oi.get(OI.Button.SHIFT_DOWN)) {
            if (Math.abs(m_left.get()) <= 0.20 && Math.abs(m_right.get()) <= 0.20) {
                driveSolenoid.set(DoubleSolenoid.Value.kForward);
                shift = false;
            }
        }
        SmartDashboard.putBoolean("shift", shift);
    }

    @Override
    public void initDefaultCommand() {
        this.setDefaultCommand(new TankDriveWithJoysticks());
    }
}
