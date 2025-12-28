package org.firstinspires.ftc.teamcode.Physical.Drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LeftRear {
    DcMotor leftRear;
    Telemetry telemetry;
    public LeftRear(Telemetry telemetry, HardwareMap hardwareMap) {
        leftRear = hardwareMap.get(DcMotor.class, "lr");
        this.telemetry = telemetry;
    }

    public void leftRearPower(double y, double x, double rx){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        double power = (y - x + rx) / denominator;
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setPower(power);
        telemetry.addData("Left Rear Power: ",power);
    }

}
