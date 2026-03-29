package org.firstinspires.ftc.teamcode.Physical.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretMotor {
    DcMotorImplEx turretMotor;
    Telemetry telemetry;
    static final double TICKS_PER_180_DEG = 400;
    static final double DEGREES_PER_180_TICKS = 180.0;

    static final double TICKS_PER_DEGREE = TICKS_PER_180_DEG / DEGREES_PER_180_TICKS;
    static final double DEGREES_PER_TICK = DEGREES_PER_180_TICKS / TICKS_PER_180_DEG;


    public TurretMotor(Telemetry telemetry, HardwareMap hardwareMap){
        turretMotor = hardwareMap.get(DcMotorImplEx.class, "turret");

        this.telemetry = telemetry;
    }


    public int degreesToTicks(double degrees) {
        return (int) Math.round(degrees * TICKS_PER_DEGREE);
    }

    public double getTurretDegrees() {
        return turretMotor.getCurrentPosition() * DEGREES_PER_TICK;
    }
    public int angleToPointDegrees(double curX, double curY, double targetPointX, double targetPointY){
        double xDifference = targetPointX - curX;
        double yDifference = targetPointY - curY; // Difference between the two points
        //angle formula: arctangent(yDiff / xDiff)
        double angleRad = (Math.atan2(yDifference, xDifference));
        //since arctangent gives back in radians, not degrees
        int angleDeg = (int)(Math.toDegrees(angleRad));
        //Integer because doesn't really get the exact heading due to sensor or human error
        return angleDeg;
    }

    public void setTurretPower(double power){
        turretMotor.setPower(power);
    }

    public void stopMotor(){
        if (turretMotor != null) turretMotor.setPower(0);
    }

    public void resetMotor(){
        turretMotor.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.FLOAT);
        turretMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        turretMotor.setMode(DcMotorImplEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}
