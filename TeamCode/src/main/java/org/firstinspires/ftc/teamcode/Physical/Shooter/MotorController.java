package org.firstinspires.ftc.teamcode.Physical.Shooter;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;


public class MotorController {
    private final DcMotorImplEx shooterMotor;
    private final double TPR = 28; // ticks per revolution
    private double targetRPM = 0;



    public MotorController(DcMotorImplEx shooterMotor) {
        this.shooterMotor = shooterMotor;


        // Reset encoder
        shooterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Optional: set PIDF coefficients
        shooterMotor.setVelocityPIDFCoefficients(12, 2.2, 7.3, 0);
    }


    /**
     * Set the motor RPM directly
     */
    public void setRPM(double rpm) {
        this.targetRPM = rpm;


        // Convert RPM to ticks per second for setVelocity
        double targetTicksPerSecond = targetRPM * TPR / 60.0;
        shooterMotor.setVelocity(targetTicksPerSecond);


    }


    /**
     * Get current motor RPM
     */
    public double getCurrentRPM() {
        return shooterMotor.getVelocity() * 60.0 / TPR;
    }


    /**
     * Stop the motor
     */
    public void stop() {
        setRPM(0);
    }

    public void max(boolean negative){
        double power = 1;
        if(negative){
            power = -1;
        }
        shooterMotor.setPower(power);
    }
}

