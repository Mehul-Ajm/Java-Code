package org.firstinspires.ftc.teamcode.Logical;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Drive.LeftFront;
import org.firstinspires.ftc.teamcode.Physical.Drive.LeftRear;
import org.firstinspires.ftc.teamcode.Physical.Drive.RightFront;
import org.firstinspires.ftc.teamcode.Physical.Drive.RightRear;

public class DriveSubsystem {

    LeftRear leftRear;
    LeftFront leftFront;
    RightFront rightFront;
    RightRear rightRear;
    Telemetry telemetry;


    public DriveSubsystem(Telemetry telemetry, HardwareMap hardwareMap){
        leftRear = new LeftRear(telemetry,hardwareMap);
        leftFront = new LeftFront(telemetry,hardwareMap);
        rightFront = new RightFront(telemetry,hardwareMap);
        rightRear = new RightRear(telemetry,hardwareMap);

        this.telemetry = telemetry;
    }

    public void moveDrive(double y, double x, double rx){
        leftFront.leftFrontPower(y, x, rx);
        leftRear.leftRearPower(y, x, rx);
        rightFront.rightFrontPower(y, x, rx);
        rightRear.rightRearPower(y, x, rx);
    }
}
