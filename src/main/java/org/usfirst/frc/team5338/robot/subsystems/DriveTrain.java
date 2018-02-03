package org.usfirst.frc.team5338.robot.subsystems;

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
import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

//Class in which Robot calls to perform all functions: specifically Drive Train
public class DriveTrain extends Subsystem {

    //Field variables that we will use

    //Talons: motor controllers that we use on the robot
    private final WPI_TalonSRX DRIVEL1 = new WPI_TalonSRX(1);
    public final WPI_TalonSRX DRIVEL2 = new WPI_TalonSRX(4);
    public final WPI_TalonSRX DRIVER1 = new WPI_TalonSRX(3);
    public final WPI_TalonSRX DRIVER2 = new WPI_TalonSRX(2);

    //Contol-group objects used to move the left drive drive and right drive motors in sync (two drive motors per side)
    private final SpeedControllerGroup m_left = new SpeedControllerGroup(this.DRIVEL1, this.DRIVEL2);
    private final SpeedControllerGroup m_right = new SpeedControllerGroup(this.DRIVER1, this.DRIVER2);
    //Creates a drive object that will define how the left and right motor sets are configured (currently as an arcade drive)
    private final DifferentialDrive DRIVE = new DifferentialDrive(this.m_left, this.m_right);

    //general variables that will be passed in to define the motion of the robot at a given moment
    private double throttle = 1.0;
    private double correctedPowerFromJoystick, turnIntensity, maxPower;
    int directionRight, directionLeft;

    //Objects that control the shift and compressor mechanism
    private final Compressor driveCompressor = new Compressor(5);
    private final DoubleSolenoid driveSolenoid = new DoubleSolenoid(5, 0, 1);
    private boolean shiftedUp;
    private final PowerDistributionPanel pdp = new PowerDistributionPanel(0);

    //Use constructor for any pre-start initialization
    public DriveTrain() {
        super();
        driveCompressor.setClosedLoopControl(true);
        driveCompressor.start();
        driveSolenoid.set(DoubleSolenoid.Value.kForward);
        shiftedUp = false;
    }

    //Runs the drive in arcade mode taking in the power magnitude toward the front and diagonal
    public void drive(final double front, final double rotate) {
        this.DRIVE.arcadeDrive(this.throttle * front, this.throttle * rotate, false);
    }

    //Actual drive method called in Robot class
    public void drive(final OI oi) {
        //Power Input cap
        maxPower = 0.5;

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

        //Checks whether the user has inputted the 'slow' command, thus reducing the speed of all motions
        if (Robot.oi.get(OI.Button.SLOW)) {
            this.throttle = 0.5;
        } else {
            this.throttle = 1.0;
        }
      
        //Uses directions and input to create a turn coefficient
        turnIntensity = Robot.oi.getLeft('X') * Math.abs(Robot.oi.getLeft('X'));
        correctedPowerFromJoystick = -directionLeft * (maxPower * Math.pow(Robot.oi.getLeft('Y'), 3) * (1 - maxPower) * (Robot.oi.getLeft('Y')));


        //If the robot is driving straight or, in case it isn't driving straight, has a throttle <= 0.6, use arcade drive
        //else use curvature drive for better handling
        if (!Robot.oi.get(OI.Button.STRAIGHT)) {
            if (Robot.oi.getLeft('Y') >= 0.6) {
                this.DRIVE.curvatureDrive(correctedPowerFromJoystick, turnIntensity, false);
            } else {
                this.DRIVE.arcadeDrive(correctedPowerFromJoystick, turnIntensity, false);
            }
        } else {
            this.DRIVE.arcadeDrive(correctedPowerFromJoystick, Robot.oi.getLeft('X'), false);
        }

        /**Shift Control System**/
        if (shiftedUp) turnIntensity *= .7; //If the gear is shifted up, reduce turn variable to reduce over-turning/aggressive turning


        if (Robot.oi.get(OI.Button.SHIFT_UP)) {
            //If the user has allowed the gear to shift up, then change the solenoid to allow for faster movement
            driveSolenoid.set(DoubleSolenoid.Value.kReverse);
            shiftedUp = true;
        } else if (Robot.oi.get(OI.Button.SHIFT_DOWN)) {
            //If user forces the gear to shift down, only do so if power is < 20%
            if (Math.abs(m_left.get()) <= 0.20 && Math.abs(m_right.get()) <= 0.20) {
                driveSolenoid.set(DoubleSolenoid.Value.kForward);
                shiftedUp = false;
            }
        }
        SmartDashboard.putBoolean("Is shifted Up", shiftedUp);
    }

    //Default drive command will be a general tank drive instead of arcade drive
    @Override
    public void initDefaultCommand() {
        this.setDefaultCommand(new TankDriveWithJoysticks());
    }
}
