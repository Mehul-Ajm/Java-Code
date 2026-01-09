package org.firstinspires.ftc.teamcode.Physical.Drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RightFront {
    DcMotor rightFront;
    Telemetry telemetry;
    public RightFront(Telemetry telemetry, HardwareMap hardwareMap) {
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        this.telemetry = telemetry;
    }

    public void rightFrontPower(double y, double x, double rx){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double power = (y - x - rx) / denominator;
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setPower(power);
        telemetry.addData("Right Front Power: ",power);
    }

}
