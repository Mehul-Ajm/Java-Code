package org.firstinspires.ftc.teamcode.Coordinator;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logical.DriveSubsystem;

public class Drivetrain {
    DriveSubsystem driveSubsystem;
    Telemetry telemetry;
    Gamepad gamepad1;

    public Drivetrain(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1){
        driveSubsystem = new DriveSubsystem(telemetry,hardwareMap);
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
    }

    public void update(double y, double x,double rx){
        driveSubsystem.moveDrive(y, x, rx);
    }
}
