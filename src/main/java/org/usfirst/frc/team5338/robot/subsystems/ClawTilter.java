package org.usfirst.frc.team5338.robot.subsystems;
import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ClawTilter extends Subsystem {
    private final WPI_TalonSRX dartTalon = new WPI_TalonSRX(31); //talon connected to the dart actuator
    private double potValue; //potentiometer value
    final double retractedValue = -190; // potentiometer value when the dart actuator is retracted
    final double extendedValue = -126; //potentiometer value when dart actuator is extended


    public ClawTilter() { super(); }//Default Constructor

    /**Method: tilt
     * @param oi
     * Action: takes in the OI and takes care of all claw tilting actions based on Operator Input
     */
    public void tilt(final OI oi){
        this.potValue = this.dartTalon.getSensorCollection().getAnalogIn(); // get the analog value of the talon on which the Dart Actuator runs

        if(Robot.oi.get(OI.Button.RAISE)){
            if(this.potValue < this.extendedValue){ //if the user wants to raise the claw and the claw hasn't hit its max yet
                final double distanceToMax = Math.abs(this.potValue - this.extendedValue); //the amount needed to extend to max
                final int slowDownRange = 15; //declares that actuator will slow 15 points away from the actuator's maximum value
                final double minSpeed = 0.10; //Speed to which the actuator will slow
                final double maxSpeed = 0.90;
                if(distanceToMax <= slowDownRange){
                    /**model the slowing down of the actuator as y = mx + b if we are within slow-down range**/
                    //d = (range in speed) / slowDownRange
                    final double deceleration = (maxSpeed - minSpeed) / slowDownRange;
                    //maxSpeed - newSpeed = d (slowDownRange - distanceToMax)
                    // -newSpeed = d (slowDownRange - distanceToMax) - maxSpeed
                    // newSpeed = maxSpeed - (d(slowDownRange - distanceToMax))
                    final double newSpeed = maxSpeed - (deceleration * (slowDownRange - distanceToMax));
                    this.dartTalon.set(newSpeed);
                }else{
                    //If we have more distance to the maximum than the 15 point limit, slow down the actuator at maximum speed until we need to decelerate
                    this.dartTalon.set(maxSpeed);
                }
            }else{
                this.dartTalon.set(0); //stop the talon if the potentiometer value is greater than the value for extension.
                //prevents jamming the actuator
            }
        }else if(Robot.oi.get(OI.Button.LOWER)){
            if(this.potValue > this.retractedValue){ //if the user wants to retract and the claw hasn't hit minimum value
                final double distanceToMin = Math.abs(this.potValue - this.retractedValue);
                final int slowDownRange = 27; //declares that the actuator will slow 27 points away from minimum value
                final double lowSpeed = 0.10; //speed to which actuator slows
                final double maxSpeed = 0.90;
                if(distanceToMin <= slowDownRange){
                    /**model the slowing down of the actuator as y = mx + b if we are within slow-down range**/
                    final double deceleration = (maxSpeed - lowSpeed) / slowDownRange; //deceleration rate as calculated in raising portion of code
                    //calculate new speed as was done in raising portion of code, but multiply by -1 to show direction change to lowering
                    final double newSpeed = (maxSpeed - (deceleration * (slowDownRange - distanceToMin))) * -1;
                    this.dartTalon.set(newSpeed);
                }else{
                    //if we are beyond slow-down range, slow down at max speed
                    this.dartTalon.set(-1 * maxSpeed);
                }
            }else{
                this.dartTalon.set(0);//stop the talon if the potentiometer value is less than the value for retraction.
                //prevents jamming the actuator
            }
        }else{
            //if the user doesn't want to raise or lower, stop the talon
            this.dartTalon.set(0);
        }

        //log the potentiometer value for testing purposes
        SmartDashboard.putNumber("Pot value", this.potValue);
    }
}
