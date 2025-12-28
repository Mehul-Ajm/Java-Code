package org.firstinspires.ftc.teamcode.Physical.Drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RightRear {
    DcMotor rightRear;
    Telemetry telemetry;
    public RightRear(Telemetry telemetry, HardwareMap hardwareMap) {
        rightRear = hardwareMap.get(DcMotor.class, "rr");
        this.telemetry = telemetry;
    }

    public void rightRearPower(double y, double x, double rx){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double power = (y + x - rx) / denominator;
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setPower(power);
        telemetry.addData("Right Rear Power: ",power);
    }

}
