package org.firstinspires.ftc.teamcode.Physical.Drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LeftFront {
    DcMotor leftFront;
    Telemetry telemetry;
    public LeftFront(Telemetry telemetry, HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, "lf");
        this.telemetry = telemetry;
    }

    public void leftFrontPower(double y, double x, double rx){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double power = (y + x + rx) / denominator;
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setPower(power);
        telemetry.addData("Left Front Power: ",power);
    }

}
