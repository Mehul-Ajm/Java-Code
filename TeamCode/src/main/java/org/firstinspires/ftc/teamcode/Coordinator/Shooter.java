package org.firstinspires.ftc.teamcode.Coordinator;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logical.ShooterCalc;
import org.firstinspires.ftc.teamcode.Logical.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Physical.Shooter.MotorController;

public class Shooter {
    ShooterSubsystem shooterSubsystem;
    Telemetry telemetry;
    Gamepad gamepad1;


    public Shooter(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1){
        shooterSubsystem = new ShooterSubsystem(telemetry,hardwareMap);


        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
    }

    public void update(boolean r, boolean l, boolean isRed){
        if(r){
            shooterSubsystem.shoot(isRed);
        }
        if(l){
            shooterSubsystem.humanPlayer();
        }
        else{
            shooterSubsystem.stopMotor();
        }
    }
}
